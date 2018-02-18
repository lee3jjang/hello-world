const SHA256 = require('crypto-js/sha256');

class Block{
    constructor(index, timestamp, data, previousHash = ''){
        this.index = index;
        this.timestamp = timestamp;
        this.data = data;
        this.previousHash = previousHash;
        this.hash = this.calculateHash();
    }

    calculateHash(){
        return SHA256(this.index + this.previousHash + this.timestamp + JSON.stringify(this.data)).toString();
    }
}

class Blockchain{
    constructor(){
        this.chain = [this.createGenesisBlock()];
    }

    createGenesisBlock(){
        return new Block(0, Date(), "This is the First Block in the world!");
    }

    getLatestBlock(){
        return this.chain[this.chain.length - 1];
    }

    addBlock(newBlock){
        newBlock.previousHash = this.getLatestBlock().hash;
        newBlock.hash = newBlock.calculateHash();
        this.chain.push(newBlock);
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
coin.addBlock(new Block(1,Date(),"Second block!"));
coin.addBlock(new Block(2,Date(),"Third block!"));

console.log(coin.isChainValid());
console.log('before : ' + coin.chain[1].calculateHash());
console.log('after : ' + coin.chain[1].hash);

console.log('------------------------------------------------------------------------');

coin.chain[1].data = 'Forth block!';
console.log(coin.isChainValid());
console.log('before : ' + coin.chain[1].calculateHash());
console.log('after : ' + coin.chain[1].hash);
