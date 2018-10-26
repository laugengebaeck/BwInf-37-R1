#include <bits/stdc++.h>
#include <boost/xpressive/xpressive.hpp>

using namespace std;
using namespace boost::xpressive;

std::locale de_locale("de_DE.UTF-8");

//funktioniert nicht mit Umlauten -> müssen ersetzt werden

string read(string infile){
    ifstream in(infile);
    in.imbue(de_locale);
    stringstream buffer;
    string instr;
    while(getline(in,instr)){
        buffer << instr << "\n";
    }
    return buffer.str();
}

void write(string output, string outfile){
    ofstream out(outfile);
    out.imbue(de_locale);
    out << output << "\n";

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
    return m[0].str();
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
        auto str = read(inputfile);
        string replaced = regex_replace(str, reg, detwist);
        write(replaced,outputfile);
    }
    return 0;
}