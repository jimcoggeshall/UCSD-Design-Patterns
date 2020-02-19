#include <iostream>
#include "GlobalCounter.hpp"

using namespace std;

int main() {

    cout << "Getting a GlobalCounter instance called gcOne" << endl;
    GlobalCounter* gcOne = GlobalCounter::getInstance();
    cout << gcOne->getCounterValue() << " ?= 0" << endl;
    cout << "Calling gcOne->incrementCounter(42)" << endl;
    gcOne->incrementCounter(42);
    cout << gcOne->getCounterValue() << " ?= 42" << endl;
    
    
    cout << "Getting a GlobalCounter instance called gcTwo" << endl;
    GlobalCounter* gcTwo = GlobalCounter::getInstance();
    cout << "Calling gcTwo->incrementCounter(8)" << endl;
    gcTwo->incrementCounter(8);
    cout << gcTwo->getCounterValue() << " ?= 50" << endl;
    cout << gcOne->getCounterValue() << " ?= 50" << endl;
    
    cout << "Deleting gcOne and gcTwo" << endl;
    delete gcOne, gcTwo;

    cout << "Getting a GlobalCounter instance called gcThree" << endl;
    GlobalCounter* gcThree = GlobalCounter::getInstance();
    cout << gcThree->getCounterValue() << " ?= 50" << endl;

    return 0;

}
