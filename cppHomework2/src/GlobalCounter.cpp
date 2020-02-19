#include <iostream>
#include "GlobalCounter.hpp"

using namespace std;

GlobalCounter* GlobalCounter::_instance = 0;

GlobalCounter::GlobalCounter() {
    global_counter = 0;
}

GlobalCounter* GlobalCounter::getInstance() {
    if (_instance == 0) {
        _instance = new GlobalCounter();
    }
    return _instance;
}

void GlobalCounter::incrementCounter(unsigned int i) {
    global_counter += i;
}

int GlobalCounter::getCounterValue() {
    return global_counter;
}
