#include <bits/stdc++.h>
#include <boost/xpressive/xpressive.hpp>
#include <boost/algorithm/string.hpp>
#define WORDLIST "woerterliste_ansi.txt"

using namespace std;
using namespace boost::xpressive;

map<string,string> wordmap;

//nur Windows 1252 (= ANSI) Dateien funktionieren

string read(string infile){
    ifstream in(infile);
    stringstream buffer;
    buffer << in.rdbuf();
    return buffer.str();
}

void write(string output, string outfile){
    ofstream out(outfile);
    out << output << "\n";

}

void gen_wordmap(void){
    ifstream in(WORDLIST);
    string word;
    while(in >> word){
        string key = string(word);
        boost::algorithm::to_lower(key);
        sort(key.begin()+1,key.end()-1);
        wordmap[key] = word;
    }
}

string twist(const boost::xpressive::smatch &m){
    auto word = m[0].str();
    char first = word[0], last = word[word.length()-1];
    if(word.length() <= 2){
        return word;
    }
    if(word.length() > 2){
        word = word.substr(1,word.length()-2);
        random_shuffle(word.begin(),word.end());
        return first + word + last;
    }
    return word;
}

string detwist(const boost::xpressive::smatch &m){
    string word = m[0].str();
    if(word.length() <= 2) return word;
    boost::algorithm::to_lower(word);
    char first = word[0], last = word[word.length()-1];

    word = word.substr(1,word.length()-2);
    sort(word.begin(),word.end());
    word = first + word + last;

    return (wordmap.count(word) == 1) ? wordmap[word] : m[0].str();
}

int main(int argc, char** argv){
    cout << "37. BwInf, 1. Runde, Aufgabe 2\n";

    if(argc != 4){
        cout << "Usage: -t <File to twist> <Output file> or -d <File to detwist> <Output file>" << "\n";
        return 0;
    }
    
    sregex reg = _b >> +(_w | "Ä" | "ä" | "Ü" | "ü" | "Ö" | "ö") >> _b;
    string mode = argv[1];
    string inputfile = argv[2];
    string outputfile = argv[3];
    
    if(mode == "-t"){
        // Twisten
        auto str = read(inputfile);
        string replaced = regex_replace(str, reg, twist);
        write(replaced,outputfile);
    } else if(mode == "-d"){
        // Detwisten
        gen_wordmap();
        auto str = read(inputfile);
        string replaced = regex_replace(str, reg, detwist);
        write(replaced,outputfile);
    }
    return 0;
}