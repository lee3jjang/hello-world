const SHA256 = require('crypto-js/sha256');

class Transaction {
    constructor(from, to, value){
        this.from = from;
        this.to = to;
        this.value = value;
    }
}



class Block{
    constructor(timestamp, transactions, previousHash = ''){
        this.timestamp = timestamp;
        this.transactions = transactions;
        this.hash = this.calculateHash();
        this.nonce = 0;
        this.previousHash = previousHash;
    }

    calculateHash(){
        return SHA256(this.previousHash + this.timestamp + JSON.stringify(this.transactions) + this.nonce).toString();
    }

    mineBlock(difficulty){
        while(this.hash.substring(0,difficulty) !== Array(difficulty + 1).join("0")){
            this.nonce++;
            this.hash = this.calculateHash();
            if(this.nonce % 100 === 0){
                console.log("Nonce:",this.nonce, "Hash:", this.hash);
            }            
        }
        console.log("\nblock mined:" + this.hash);
        console.log("Nonce:" + this.nonce);
    }
}

class Blockchain{
    constructor(){
        this.chain = [this.createGenesisBlock()];
        this.difficulty = 3;
        this.pendingTransactions = [];
        this.Reward = 10000;
    }

    createGenesisBlock(){
        return new Block(Date.now(), []);
    }

    getLatestBlock(){
        return this.chain[this.chain.length - 1];
    }

    minePendingTransactions(RewardAddr){
        let block = new Block(Date.now(), this.pendingTransactions, this.getLatestBlock().hash);
        block.mineBlock(this.difficulty);
        console.log('\nBlock successfully mined!');
        this.chain.push(block);
        this.pendingTransactions = [
            new Transaction(null, RewardAddr, this.Reward)
        ];
    }

    createTransaction(transaction){
        this.pendingTransactions.push(transaction);
    }

    getBalance(addr){
        let balance = 0;
        for(const block of this.chain){
            for(const trans of block.transactions){
                if(trans.from === addr){
                    balance -= trans.value;
                }
                if(trans.to === addr){
                    balance += trans.value;
                }
            }
        }
        return balance;
    }

    isChainValid(){
        for(let i=1; i<this.chain.length;i++){
            const currentBlock = this.chain[i];
            const previousBlock = this.chain[i-1];

            if(currentBlock.hash !== currentBlock.calculateHash()){
                return false;
            }

            if(currentBlock.previousHash !== previousBlock.hash){
                return false;
            }

            return true;
        }
    }
}

coin = new Blockchain();
coin.createTransaction(new Transaction('sangjin', 'jiwon', 1000));
coin.createTransaction(new Transaction('jiwon', 'sangjin', 500));
console.log(coin.pendingTransactions);

console.log('\nStarting the miner.');
coin.minePendingTransactions('sangjin');
console.log('\n',coin.pendingTransactions);

console.log('\nBalance of sangjin is...', coin.getBalance('sangjin'));
console.log('Balance of jiwon is...', coin.getBalance('jiwon'));
console.log('\n',coin);
