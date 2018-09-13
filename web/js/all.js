function phoneValidate(phonenum) {
    if (phonenum == "") {
        return true;
    }
    var reg = /^[1][3,4,5,7,8][0-9]{9}$/;
    if (!reg.test(phonenum)) {
        return false;
    } else {
        return true;
    }
}

function idCardValidate(card) {
    if (card == "") {
        return true;
    }
    // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    if (reg.test(card) === false) {
        return false;
    }
    return true;
}

function emailValidate(email) {
    if (email == "") {
        return true;
    }
    var reg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
    if (!reg.test(email)) {
        return false;
    } else {
        return true;
    }
}

function qqValidate(qq) {
    if (qq == "") {
        return true;
    }
    var reg = /^[1-9]\d{4,12}$/;
    if (!reg.test(qq)) {
        return false;
    } else {
        return true;
    }
}

function limit8Validate(str) {
    if (str == "") {
        return true;
    }
    var reg =/^[A-Za-z0-9_]{1,8}$/;
    if (!reg.test(str)) {
        return false;
    } else {
        return true;
    }
}

function limit20Validate(str) {
    if (str == "") {
        return true;
    }
    var reg = /^.{1,20}$/;
    if (!reg.test(str)) {
        return false;
    } else {
        return true;
    }
}
function limit6Validate(str) {
    if (str == "") {
        return true;
    }
    var reg = /^.{1,6}$/;
    if (!reg.test(str)) {
        return false;
    } else {
        return true;
    }
}

function limit30Validate(str) {
    if (str == "") {
        return true;
    }
    var reg = /^[A-Za-z0-9_]{1,30}$/;
    if (!reg.test(str)) {
        return false;
    } else {
        return true;
    }
}
