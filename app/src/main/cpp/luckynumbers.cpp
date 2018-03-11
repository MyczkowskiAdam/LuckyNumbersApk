//
// Created by mycax on 08/04/2017.
//
#include <jni.h>
#include <string>
#include <sstream>
#include <android/log.h>

#define  LOG_TAG    "testjni"
#define  ALOG(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

/* VARIABLES USED */
const std::string alphabet = "abcdefghijklmnopqrstuvwxyz"; //Letters of alphabet used throughout the program
const std::string CAPalphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //Capital letters of alphabet used throughout the program
/* END VARIABLES LIST*/

template <typename T>
std::string ToString(T pNumber)
{
    std::ostringstream oOStrStream;
    oOStrStream << pNumber;
    return oOStrStream.str();
}

int convert(std::string Nm) {
    char *NmAr = const_cast<char*>(Nm.c_str()); // converts a string into char* (pointer to a char, basically a char array)

    int NmVal = NULL;
    for (unsigned int i = 0; i<Nm.length(); i++) {
        int tmp = (int) alphabet.find(NmAr[i]); // This function takes a char from char*, tries to find it in the alphabet string, returns its positiion as a integer and writes results to tmp integer
        if (tmp == std::string::npos) { // npos (-1) is returned when char we looking for is not in a given string (no position)
            tmp = (int) CAPalphabet.find(NmAr[i]); // This function takes a char from char*, tries to find it in the CAPalphabet string, returns its positiion as a integer and writes results to tmp integer
            if (tmp == std::string::npos) { // npos (-1) is returned when char we looking for is not in a given string (no position)
                ALOG("Error at line %d in tmp is %i", __LINE__,  tmp);
                break;
            }
        }
        NmVal += (tmp % 9) + 1; // Use simple mathematical equation to change the tmp int into a right number as in the cheatsheet, for example 'c' is in pos 2, 2 % 9 = 2 because 2 / 9 = 0.(2), 2 + 1 = 3. ^Lookup cheatsheet: c = 3.
    }
    return NmVal;
}

int splitNumber(int Number) {
    int dig1, dig2;
    while (Number > 9) { // if number is lower than 9 it's a single digit and doesn't need to be split
        dig1 = (Number / 10) % 10;
        dig2 = Number % 10;
        if (Number > 99) {
            int dig3 = (Number / 100) % 10;
            Number = dig1 + dig2 + dig3;
        }
        else {
            Number = dig1 + dig2;
        }
    }
    return Number;
}
int LuckyNumbersProcess(std::string FrNm, std::string LstNm) {
    int FrNmVal = convert(FrNm);
    FrNmVal = splitNumber(FrNmVal);
    int LstNmVal = convert(LstNm);
    LstNmVal = splitNumber(LstNmVal);
    int LuckyNumber = FrNmVal + LstNmVal;
    return splitNumber(LuckyNumber);
}

extern "C"
JNIEXPORT jint JNICALL Java_com_luckynumbers_mycax_luckynumbers_LuckyNumbersFragment_Calculate (JNIEnv *env, jobject, jstring cFirstName, jstring cLastName) {
    return LuckyNumbersProcess(ToString(env->GetStringUTFChars(cFirstName, NULL )),
                                                 ToString(env->GetStringUTFChars(cLastName, NULL)));
}