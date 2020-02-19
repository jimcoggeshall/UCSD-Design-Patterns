* To create javadoc and executable jar:

mvn clean package

* To view javadoc (for example):

firefox target/site/apidocs/index.html

* To run driver:

java -jar target/Homework2-1.0.jar

* Example output:

[jcc@Jamess-Air Homework2]$ java -jar target/Homework2-1.0.jar 
Getting a GlobalCounter instance called gcOne
gcOne.getCounterValue() = 0 - should be 0
Calling gcOne.incrementCounter(42)
gcOne.getCounterValue() = 42 - should be 42
Getting a GlobalCounter instance called gcTwo
Calling gcTwo.incrementCounter(8)
gcTwo.getCounterValue() = 50 - should be 50
gcOne.getCounterValue() = 50 - should be 50
Throwing away references to gcOne and gcTwo
Getting a GlobalCounter instance called gcThree
gcThree.getCounterValue() = 50 - should be 50
Calling GlobalCounter.getInstance().incrementCounter(10)
gcThree.getCounterValue() = 60 - should be 60
Finished
[jcc@Jamess-Air Homework2]$
