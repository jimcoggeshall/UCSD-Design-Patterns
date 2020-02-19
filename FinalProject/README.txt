*Instructions

Executable jar is: target/FinalProject-1.0.jar
Example output:
    [jcc@jcc-ubuntu-1510 FinalProject]$ java -jar target/FinalProject-1.0.jar
    Creating an InputSourceFactory for input file src/main/resources/PurchasingCards.json...
    ABSTRACT FACTORY: Using this InputSourceFactory to create InputParser and Stream objects...
    Created InputParser jsonParser via InputSourceFactory.createInputParser(): edu.ucsd.dp.finalproject.JsonInputParser@7eda2dbb
    Created Stream jsonStream via InputSourceFactory.createInputStream(): java.util.stream.ReferencePipeline$Head@6576fe71
    ABSTRACT FACTORY: Creating an InputSourceFactory for input file src/main/resources/PurchasingCards.csv...
    Created InputParser csvParser via InputSourceFactory.createInputParser(): edu.ucsd.dp.finalproject.DelimitedInputParser@6193b845
    Created Stream csvStream via InputSourceFactory.createInputStream(): java.util.stream.SliceOps$1@2e817b38
    COMMAND: Creating a FieldTransformer to convert the "DATE" field in both input files to a Date object...
    BUILDER: Building an InputTransformer containing the existing FieldTransformer...
    Executing on the dataset:
    ((jsonInput ->(parseJson)-> parsedMap) ++ (csvInput ->(parseCsv)-> parsedMap))
        ->(transform)-> parsedTransformedMap
        ->(filter)-> parsedTransformedFilteredMap
        ->(map)-> accountNumber
        ->(reduce)-> uniqueAccountNumber
        ->(count)-> total
    Number of cards active on or after June 1 2010 = 1589
    [jcc@jcc-ubuntu-1510 FinalProject]$ 



*Overview

The purpose of these tools is to provide a flexible, robust, and generic framework by which some
of our more persistent data-ingestion tasks may be streamlined. In particular, the aim here is to 
create generic, reusable components whose design allows for extensibility in as straightforward a
manner as possible, both by isolating the (relatively small amount of) intelligence neeed to 
implement a task using these tools and by eliminating all unnecessary components. 

In general, these kinds of tasks involve, for each data source individually (simultaneously):
 - Establishing a channel by which the raw data is read
 - Attaching a parser whose task is to parse the data into usable objects
 - Applying a transformation operation to specific elements of the parsed objects
 - Downstream operations
At each stage, there may be arbitrarily many data streams, which may be combined as necessary. 

The Driver class produces a demonstration of a typical scenario in which a fictional client has
delivered us data (the data used here is publicly available) in CSV format but has left out an 
important account from that dataset--the single most active account. The client then sent us the
missing transactions but did so in a different format. Our task is to count the number of active 
cards in the second half of the year, including both datasets. 

An abstract factory pattern is used to create concrete instances of objects whose roles are to: 
(a) create and initialize the input stream, and
(b) encapsulate the parsing logic needed to turn one record of the input stream into a Map.
These concrete products are, in our case, not totally orthogonal to one another. In the case in 
which we wish to parse a json dataset, each row is self-describing; however, in the csv case, the
parser must first read the header line in order to map each field to the correct label. Furthermore
the stream must not include the header line, since that line represents metadata. The concrete
factory CsvInputSourceFactory contains the relevant implementation, and the unit test 
TestAbstractInputFactory verifies that these two factories yield identical results on datasets that
are identical except for their format.

The input is then passed through a "transformation" layer implemented by InputTransformer. This 
layer may contain arbitrarily many field-level transformations, implemented by FieldTransformer, 
and is created with a builder pattern in which this composition is made explicit. In our example
we apply a transformation to the "DATE" field, so we can perform datewise operations later. 

The intent of FieldTransformer is specifically to create an object whose sole purpose is to carry
out the transformation operation at the specified point in the pipeline, using a method "execute"
which encapsulates all necessary intelligence, as in the command pattern.

Finally, it is not used in the Driver example, but an important aspect of a complete toolkit along 
these lines in our current work environment is the ability to read not from a file but from a 
socket. The adapter pattern is used to create SocketStreamAdapter, which allows for reading input
from a TCP socket on a specified port rather than a file. Only "self-describing" formats may be 
ingested this way; thus this operation is not allowed in CsvInputSourceFactory, but only in 
JsonInputSourceFactory. This further underscores the utility of the abstract factory pattern. 
The unit test TestJsonInputSourceFactory contains an example application in which the socket 
adapter is used. 



*Creational

Builder pattern:
    src/main/java/edu/ucsd/dp/finalproject/InputTransformerBuilder.java

Abstract factory pattern:
    src/main/java/edu/ucsd/dp/finalproject/InputSourceFactory.java
    src/main/java/edu/ucsd/dp/finalproject/CsvInputSourceFactory.java
    src/main/java/edu/ucsd/dp/finalproject/InputSourceFactory.java

*Structural

Adapter pattern:
    src/main/java/edu/ucsd/dp/finalproject/SocketStreamAdapter.java


*Behavioral

Command pattern:
    src/main/java/edu/ucsd/dp/finalproject/FieldTransformer.java
