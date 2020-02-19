appname := counter

CXX := g++
CXXFLAGS := -Wall -g -Ilib

incfiles := $(shell find . -maxdepth 10 -name "*.hpp")
srcfiles := $(shell find . -maxdepth 10 -name "*.cpp")
depfiles := $(incfiles)
depfiles += $(srcfiles)
objects  := $(patsubst %.cpp, %.o, $(srcfiles))

all: $(appname)

$(appname): $(objects)
	$(CXX) $(CXXFLAGS) $(LDFLAGS) -o $(appname) $(objects) $(LDLIBS)

depend: .depend

.depend: $(depfiles)
	rm -f ./.depend
	$(CXX) $(CXXFLAGS) -MM $^>>./.depend;

clean:
	rm -f $(objects) $(appname)

dist-clean: clean
	rm -f *~ .depend

include .depend
