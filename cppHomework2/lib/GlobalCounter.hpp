#ifndef GLOBAL_CONTEXT_HPP
#define GLOBAL_CONTEXT_HPP

class GlobalCounter {

    int global_counter;

public:
    static GlobalCounter* getInstance();
    void incrementCounter(unsigned int);
    int getCounterValue();

private:
    GlobalCounter();
    static GlobalCounter* _instance;

};

#endif
