const express = require('express');
let router = express.Router();

router.get('/:id', (req, res, next) => {
    res.send('유저 정보 가져오기');
});

router.post('/', (req, res, next) => {
    res.send('회원가입');
});

router.put('/:id', (req, res, next) => {
    res.send('유저 정보 수정');
});

router.delete('/:id', (req, res, next) => {
    res.send('회원탈퇴');
});

module.exports = router;