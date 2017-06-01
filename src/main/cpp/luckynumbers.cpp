//
// Created by mycax on 08/04/2017.
//
#include <jni.h>
#include <string>
#include <sstream>

/* VARIABLES USED */
const std::string lucky[] = {
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


const std::string alphabet = "abcdefghijklmnopqrstuvwxyz"; //Letters of alphabet used throughout the program
const std::string CAPalphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //Capital letters of alphabet used throughout the program

int FrNmVal, LstNmVal, tmp, LuckyNumber, dig1, dig2, dig3;

std::string FrNmFromJava, LstNmFromJava, NumberAndMeaning;
char *FrNmAr, *LstNmAr;

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
        tmp = int(alphabet.find(FrNmAr[i])); // This function takes a char from char*, tries to find it in the alphabet string, returns its positiion as a integer and writes results to tmp integer
        if (tmp == std::string::npos) { // npos (-1) is returned when char we looking for is not in a given string (no position)
            tmp = int(CAPalphabet.find(FrNmAr[i])); // This function takes a char from char*, tries to find it in the CAPalphabet string, returns its positiion as a integer and writes results to tmp integer
            if (tmp == std::string::npos) { // npos (-1) is returned when char we looking for is not in a given string (no position)
                break;
            }
        }
        FrNmVal += (tmp % 9) + 1; // Use simple mathematical equation to change the tmp int into a right number as in the cheatsheet, for example 'c' is in pos 2, 2 % 9 = 2 because 2 / 9 = 0.(2), 2 + 1 = 3. ^Lookup cheatsheet: c = 3.
    }

    LstNmAr = const_cast<char*>(LstNm.c_str()); // converts a string into char* (pointer to a char, basically a char array)

    for (unsigned int i = 0; i<LstNm.length(); i++) {
        tmp = int(alphabet.find(LstNmAr[i])); // This function takes a char from char*, tries to find it in the alphabet string, returns its positiion as a integer and writes results to tmp integer
        if (tmp == std::string::npos) { // npos (-1) is returned when char we looking for is not in a given string (no position)
            tmp = int(CAPalphabet.find(LstNmAr[i])); // This function takes a char from char*, tries to find it in the CAPalphabet string, returns its positiion as a integer and writes results to tmp integer
            if (tmp == std::string::npos) { // npos (-1) is returned when char we looking for is not in a given string (no position)
                break;
            }
        }
        LstNmVal += (tmp % 9) + 1; // Use simple mathematical equation to change the tmp int into a right number as in the cheatsheet, for example 'c' is in pos 2, 2 % 9 = 2 because 2 / 9 = 0.(2), 2 + 1 = 3. ^Lookup cheatsheet: c = 3.
    }

    while (FrNmVal > 9) { // if number is lower than 9 it's a single digit and doesn't need to be split
        dig1 = (FrNmVal / 10) % 10;
        dig2 = FrNmVal % 10;
        if (FrNmVal > 99) {
            dig3 = (FrNmVal / 100) % 10;
            FrNmVal = dig1 + dig2 + dig3;
        }
        else {
            FrNmVal = dig1 + dig2;
        }
    }

    while (LstNmVal > 9) { // if number is lower than 9 it's a single digit and doesn't need to be split
        dig1 = (LstNmVal / 10) % 10;
        dig2 = LstNmVal % 10;
        if (LstNmVal > 99) {
            dig3 = (LstNmVal / 100) % 10;
            LstNmVal = dig1 + dig2 + dig3;
        }
        else {
            LstNmVal = dig1 + dig2;
        }
    }

    LuckyNumber = FrNmVal + LstNmVal;

    while (LuckyNumber > 9) { // if number is lower than 9 it's a single digit and doesn't need to be split
        dig1 = (LuckyNumber / 10) % 10;
        dig2 = LuckyNumber % 10;
        if (LuckyNumber > 99) {
            dig3 = (LuckyNumber / 100) % 10;
            LuckyNumber = dig1 + dig2 + dig3;
        }
        else {
            LuckyNumber = dig1 + dig2;
        }
    }

    NumberAndMeaning = "Your lucky number is " + ToString(LuckyNumber) + "!! This means that you " + lucky[LuckyNumber - 1];
    return 0;
}

void ClearAll() {
    FrNmVal = LstNmVal = tmp = LuckyNumber = 0;
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