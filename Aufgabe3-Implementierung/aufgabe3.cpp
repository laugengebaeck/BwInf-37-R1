#include <bits/stdc++.h>
#define MAXN 2000
#define M 10

using namespace std;

typedef pair<int,int> pii;
typedef pair<pii,pii> ppii;

vector<int> set_numbers;
vector<int> al_numbers(10,0);
vector<pii> blocks;

long in_money = 0;
long out_money = 0;

struct Gapcmp{
    bool operator()(const ppii &a, const ppii &b){
        return a.second.second - a.first.first + 1 < b.second.second - b.first.first + 1;
    }
};

void choose_median(){
    int num_idx = 0;
    if(blocks.size() > 10){
        cerr << "Zu viele Blöcke. Breche ab\n";
        return;
    } 
    for(size_t i = 0; i < blocks.size(); i++){
        vector<int> elms;
        while(set_numbers[num_idx] < blocks[i].second){
            elms.push_back(set_numbers[num_idx]);
            num_idx++;
        }
        int median = 0;
        if(elms.size() % 2 == 1 && elms.size() > 1){
            median = 0.5 * (elms[elms.size() / 2 - 1] + elms[elms.size() / 2]);
        } else if(elms.size() % 2 == 0 && elms.size() > 0) {
            median = elms[elms.size() / 2];
        } else if(elms.size() == 1){
            median = elms[0];
        } else {
            cerr << "Leerer Block!\n";
            continue;
        }
        al_numbers[i] = median;
        for(size_t j = 0; j < elms.size(); j++){
            cout << elms[j] << "\n";
            out_money += abs(elms[j] - median);
        }
        cout << "\n";
    }
    
}

int main(int argc, char** argv) {
    cout << "37. BwInf, 1. Runde, Aufgabe 3\n";

    if(argc < 2){
        return 0;
    }
    ifstream in(argv[1]);
    
    unsigned int z, C = 0 ;
    while(in >> z){
        C++;
        if(find(set_numbers.begin(),set_numbers.end(),z) == set_numbers.end()) blocks.push_back(make_pair(z,z));
        set_numbers.push_back(z);
    }
    in_money = 25 * C;

    sort(set_numbers.begin(),set_numbers.end());
    sort(blocks.begin(),blocks.end());

    set<ppii,Gapcmp> gaps;
    
    for(size_t i = 0; i < blocks.size() - 1; i++){
        // create gap
        gaps.insert(make_pair(blocks[i],blocks[i+1]));
    }
    
    while(blocks.size() > M){
        ppii shortest = *gaps.begin();
        gaps.erase(shortest);
        auto iter = find(blocks.begin(),blocks.end(),shortest.first);
        if(iter != blocks.end()){
            int j = distance(blocks.begin(),iter);
            // Vorgänger-gap löschen
            if(j > 0) gaps.erase(make_pair(blocks[j-1],blocks[j]));
            // Nachfolger-Gap löschen
            gaps.erase(make_pair(blocks[j+1],blocks[j+2]));
            // Blocks zusammenfügen
            blocks[j].second = blocks[j+1].second;
            // Vorgänger-Gap updaten
            if(j > 0) gaps.insert(make_pair(blocks[j-1],blocks[j]));
            // Nachfolger-Gap updaten
            gaps.insert(make_pair(blocks[j],blocks[j+2]));
            blocks.erase(blocks.begin() + j + 1);
        }        
    }

    choose_median();

    cout << "Al wählt folgende Zahlen:\n";
    for(size_t i = 0; i < 10; i++){
        cout << al_numbers[i] << "\n";
    }
    
    cout << "Al zahlt " << out_money << " Dollar aus.\n";
    cout << "Er macht damit " << in_money - out_money << " Dollar Gewinn\n";
    
    return 0;
}