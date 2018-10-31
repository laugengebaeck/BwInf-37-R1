#include <bits/stdc++.h>
#include <boost/xpressive/xpressive.hpp>
#define WORDLIST "woerterliste_ansi.txt"

using namespace std;
using namespace boost::xpressive;

map<string,string> wordmap;
//nur Windows 1252 (= ANSI) Dateien funktionieren

// Eingabe
string read(string infile){
    ifstream in(infile);
    stringstream buffer;
    buffer << in.rdbuf();
    return buffer.str();
}

// Ausgabe
void write(string output, string outfile){
    ofstream out(outfile);
    out << output << flush;
}

// Map generieren
void gen_wordmap(void){
    ifstream in(WORDLIST);
    string word;
    while(in >> word){
        string key = string(word);
        sort(key.begin()+1,key.end()-1);
        wordmap[key] = word;
    }
}

string twist(const boost::xpressive::smatch &m){
    auto word = m[0].str();
    char first = word[0], last = word[word.length()-1];
    if(word.length() <= 2){
        // Sonderfall: mittlerer Teil leer
        return word;
    } else {
        // zufälliges Shufflen des mittleren Teils
        word = word.substr(1,word.length()-2);
        random_shuffle(word.begin(),word.end());
        return first + word + last;
    }
}

string detwist(const boost::xpressive::smatch &m){
    string word = m[0].str();
    if(word.length() <= 2) {
        // Sonderfall: mittlerer Teil leer
        return word;
    } else {
        // Anwendung der Hashfunktion
        sort(word.begin()+1,word.end()-1);
        // Nachschlagen in der Map
        return (wordmap.count(word) == 1) ? wordmap[word] : m[0].str();
    }
}

int main(int argc, char** argv){
    // Benutzungshinweise
    cout << "37. BwInf, 1. Runde, Aufgabe 2\n";
    if(argc != 4){
        cout << "Usage: -t <File to twist> <Output file> or -d <File to detwist> <Output file>" << "\n";
        return 0;
    }
    
    // Regex + Konsolenargumente
    sregex reg = _b >> +(_w | "Ä" | "ä" | "Ü" | "ü" | "Ö" | "ö") >> _b;
    string mode = argv[1];
    string inputfile = argv[2];
    string outputfile = argv[3];
    
    if(mode == "-t"){
        // Twisten und I/O
        auto str = read(inputfile);
        string replaced = regex_replace(str, reg, twist);
        write(replaced,outputfile);
    } else if(mode == "-d"){
        // Enttwisten und I/O
        gen_wordmap();
        auto str = read(inputfile);
        string replaced = regex_replace(str, reg, detwist);
        write(replaced,outputfile);
    }
    return 0;
}