#include <iostream>
#include <limits.h>
#include <random>
#include <chrono>
#include <list>
#include <vector>
#include <string>
#include <algorithm>
#include <iomanip>
#include <stdio.h>
#include <fstream>

static std::random_device rd;
static std::mt19937 gen(rd());
static std::uniform_int_distribution<long long> dist(LONG_MIN, LONG_MAX);

static long long executionTime = 0;

long long* mergeSorted(long long* firstArray, int firstArraySize, long long* secondArray, int secondArraySize)
{
    auto startTime = std::chrono::high_resolution_clock::now();
    long long* output = new long long[firstArraySize + secondArraySize];

    int i = 0, j = 0;
    while(i < firstArraySize && j < secondArraySize) {
        if(firstArray[i] > secondArray[j])
            output[i + j] = secondArray[j++];
        else
            output[i + j] = firstArray[i++];
    }
    for(; i < firstArraySize; i++)
        output[i + j] = firstArray[i];
    for(; j < secondArraySize; j++)
        output[i + j] = secondArray[j];

    auto endTime = std::chrono::high_resolution_clock::now();
    executionTime = std::chrono::duration_cast<std::chrono::nanoseconds>(endTime - startTime).count();
    return output;
}

std::list<long long> mergeSorted(std::list<long long> firstList, std::list<long long> secondList)
{
    auto startTime = std::chrono::high_resolution_clock::now();
    std::list<long long> output;
    std::list<long long>::const_iterator i = firstList.begin(), j = secondList.begin();

    while(i != firstList.end() && j != secondList.end()) {
        if(*i > *j) {
            output.push_back(*i);
            i++;
        } else {
            output.push_back(*j);
            j++;
        }
    }
    for(; i != firstList.end(); i++)
        output.push_back(*i);
    for(; j != secondList.end(); j++)
        output.push_back(*j);

    auto endTime = std::chrono::high_resolution_clock::now();
    executionTime = std::chrono::duration_cast<std::chrono::nanoseconds>(endTime - startTime).count();
    return output;
}

std::vector<long long> mergeSorted(std::vector<long long> firstVector, std::vector<long long> secondVector)
{
    auto startTime = std::chrono::high_resolution_clock::now();
    std::size_t firstVectorSize = firstVector.size(), secondVectorSize = secondVector.size();
    std::vector<long long> output = std::vector<long long>(firstVectorSize + secondVectorSize);

    int i = 0, j = 0;
    while(i < firstVectorSize && j < secondVectorSize) {
        if(firstVector[i] > secondVector[j])
            output[i + j] = secondVector[j++];
        else
            output[i + j] = firstVector[i++];
    }
    for(; i < secondVectorSize; i++)
        output[i + j] = firstVector[i];
    for(; j < secondVectorSize; j++)
        output[i + j] = secondVector[j];

    auto endTime = std::chrono::high_resolution_clock::now();
    executionTime = std::chrono::duration_cast<std::chrono::nanoseconds>(endTime - startTime).count();
    return output;
}

long long* createRandomArray(std::size_t n)
{
    long long *arr = new long long[n];
    for(int i = 0; i < n; i++)
        arr[i] = dist(gen);
    return arr;
}

long long* createSortedArray(std::size_t size)
{
    long long* array = createRandomArray(size);
    std::sort(array, array + size);
    return array;
}

std::list<long long> createListFromArray(long long* array, std::size_t size)
{
    std::list<long long> list;
    for(int i = 0; i < size; i++)
        list.push_back(array[i]);
    return list;
}

bool isDigit(const std::string& str)
{
    std::string::const_iterator it = str.begin();
    for(; it != str.end() && isdigit(*it); it++);
    return !str.empty() && it == str.end();
}

int main(int argc, char** argv)
{
    std::vector<std::string> args(&argv[0], &argv[0 + argc]);
    if(argc != 4 || !isDigit(args[1]) || !isDigit(args[2]) || !isDigit(args[3])) {
        std::cout << "Invalid command line arguments" << std::endl;
        exit(EXIT_FAILURE);
    }

    std::size_t m = std::stoi(args[1]), n1 = std::stoi(args[2]), n2 = std::stoi(args[3]);
    long long arrayTime = 0, listTime = 0, vectorTime = 0;
    std::cout << "C++ 11:" << std::endl << "Task 2:" << std::endl;
    std::cout << "m = " << m << " n1 = " << n1 << " n2 = " << n2 << std::endl;
    std::cout << "Merge two sorted arrays:" << std::endl;

    long long* array1;
    long long* array2;
    for(int i = 0; i < m; i++) {
        array1 = createSortedArray(n1);
        array2 = createSortedArray(n2);

        long long* array3 = mergeSorted(array1, n1, array2, n2);
        delete array3;
        arrayTime += executionTime;

        mergeSorted(createListFromArray(array1, n1), createListFromArray(array2, n2));
        listTime += executionTime;


        mergeSorted(std::vector<long long>(array1, array1 + n1), std::vector<long long>(array2, array2 + n2));
        vectorTime += executionTime;

        delete array1, array2;
    }

    std::cout.precision(4);
    std::cout << "Array:   " << std::setw(9) << std::setfill(' ') << arrayTime / m << " ns"
        << std::setw(9) << std::setfill(' ') << 1 << " arrayTime" << std::endl;
    std::cout << "List:    " << std::setw(9) << std::setfill(' ') << listTime / m << " ns"
        << std::setw(9) << std::setfill(' ') << (long double)listTime / arrayTime << " arrayTime" << std::endl;
    std::cout << "Vector:  " << std::setw(9) << std::setfill(' ') << vectorTime / m << " ns"
        << std::setw(9) << std::setfill(' ') << (long double)vectorTime / arrayTime << " arrayTime" << std::endl;

}
