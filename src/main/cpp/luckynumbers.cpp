//
// Created by mycax on 08/04/2017.
//
#include <jni.h>
#include <string>
#include <sstream>

/* VARIABLES USED */
std::string lucky[] = {
        "are a natural leader",
        "are a natural peacemaker",
        "are creative and optimistic",
        "are a hard worker",
        "value freedom",
        "are a carer and provider",
        "are a thinker",
        "have diplomatic skills",
        "are selfless and generous"
};


std::string alphabet = "abcdefghijklmnopqrstuvwxyz"; //Letters of alphabet used throughout the program
std::string CAPalphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //Capital letters of alphabet used throughout the program

int FrNmVal, LstNmVal, tmp, LuckyNumber;

std::string FrNmValS, LstNmValS, LuckyNumberS, FrNmFromJava, LstNmFromJava, NumberAndMeaning;
char *FrNmValAr, *LstNmValAr, *LuckyNumberAr, *FrNmAr, *LstNmAr;

/* END VARIABLES LIST*/

template <typename T>
std::string ToString(T pNumber)
{
    std::ostringstream oOStrStream;
    oOStrStream << pNumber;
    return oOStrStream.str();
}

int LuckyNumbersProcess(std::string FrNm, std::string LstNm) {
    FrNmAr = const_cast<char*>(FrNm.c_str()); // converts a string into char* (pointer to a char, basically a char array)

    for (unsigned int i = 0; i<FrNm.length(); i++) {
        tmp = alphabet.find(FrNmAr[i]); // This function takes a char from char*, tries to find it in the alphabet string, returns its positiion as a integer and writes results to tmp integer
        if (tmp == std::string::npos) { // npos (-1) is returned when char we looking for is not in a given string (no position)
            tmp = CAPalphabet.find(FrNmAr[i]); // This function takes a char from char*, tries to find it in the CAPalphabet string, returns its positiion as a integer and writes results to tmp integer
            if (tmp == std::string::npos) { // npos (-1) is returned when char we looking for is not in a given string (no position)
                break;
            }
        }
        FrNmVal += (tmp % 9) + 1; // Use simple mathematical equation to change the tmp int into a right number as in the cheatsheet, for example 'c' is in pos 2, 2 % 9 = 2 because 2 / 9 = 0.(2), 2 + 1 = 3. ^Lookup cheatsheet: c = 3.
    }

    LstNmAr = const_cast<char*>(LstNm.c_str()); // converts a string into char* (pointer to a char, basically a char array)

    for (unsigned int i = 0; i<LstNm.length(); i++) {
        tmp = alphabet.find(LstNmAr[i]); // This function takes a char from char*, tries to find it in the alphabet string, returns its positiion as a integer and writes results to tmp integer
        if (tmp == std::string::npos) { // npos (-1) is returned when char we looking for is not in a given string (no position)
            tmp = CAPalphabet.find(LstNmAr[i]); // This function takes a char from char*, tries to find it in the CAPalphabet string, returns its positiion as a integer and writes results to tmp integer
            if (tmp == std::string::npos) { // npos (-1) is returned when char we looking for is not in a given string (no position)
                break;
            }
        }
        LstNmVal += (tmp % 9) + 1; // Use simple mathematical equation to change the tmp int into a right number as in the cheatsheet, for example 'c' is in pos 2, 2 % 9 = 2 because 2 / 9 = 0.(2), 2 + 1 = 3. ^Lookup cheatsheet: c = 3.
    }

    while (FrNmVal > 9) { // if number is lower than 9 it's a single digit and doesn't need to be split
        FrNmValS = ToString(FrNmVal);
        FrNmValAr = const_cast<char*>(FrNmValS.c_str());
        if (FrNmVal > 99) {
            FrNmVal = (FrNmValAr[0] - '0') + (FrNmValAr[1] - '0') + (FrNmValAr[2] + '0');
        }
        else {
            FrNmVal = (FrNmValAr[0] - '0') + (FrNmValAr[1] - '0');
        }
    }

    while (LstNmVal > 9) { // if number is lower than 9 it's a single digit and doesn't need to be split
        LstNmValS = ToString(LstNmVal);
        LstNmValAr = const_cast<char*>(LstNmValS.c_str());
        if (LstNmVal > 99) {
            FrNmVal = (LstNmValAr[0] - '0') + (LstNmValAr[1] - '0') + (LstNmValAr[2] + '0');
        }
        else {
            LstNmVal = (LstNmValAr[0] - '0') + (LstNmValAr[1] - '0');
        }
    }

    LuckyNumber = FrNmVal + LstNmVal;

    while (LuckyNumber > 9) { // if number is lower than 9 it's a single digit and doesn't need to be split
        LuckyNumberS = ToString(LuckyNumber);
        LuckyNumberAr = const_cast<char*>(LuckyNumberS.c_str());
        if (LuckyNumber > 99) {
            LuckyNumber = (LuckyNumberAr[0] - '0') + (LuckyNumberAr[1] - '0') + (LuckyNumberAr[2] + '0');
        }
        else {
            LuckyNumber = (LuckyNumberAr[0] - '0') + (LuckyNumberAr[1] - '0');
        }
    }

    NumberAndMeaning = "Your lucky number is " + ToString(LuckyNumber) + "!! This means that you " + lucky[LuckyNumber - 1];
    return 0;
}

void ClearAll() {
    FrNmVal = LstNmVal = tmp = LuckyNumber = 0;
    FrNmValS.clear();
    LstNmValS.clear();
    LuckyNumberS.clear();
    FrNmFromJava.clear();
    LstNmFromJava.clear();
    NumberAndMeaning.clear();
}

extern "C"
JNIEXPORT jstring JNICALL Java_com_android_mycax_luckynumbers_MainActivity_Calculate (JNIEnv *env, jobject, jstring cFirstName, jstring cLastName) {
    const char *path = env->GetStringUTFChars(cFirstName, NULL );
    const char *path2 = env->GetStringUTFChars(cLastName, NULL);
    FrNmFromJava = ToString(path);
    LstNmFromJava = ToString(path2);
    LuckyNumbersProcess(FrNmFromJava, LstNmFromJava);
    std::string sLuckyNumber = NumberAndMeaning;
    ClearAll();
    return env->NewStringUTF(sLuckyNumber.c_str());
}