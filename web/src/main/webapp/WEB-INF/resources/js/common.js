function numberFormatter(inputElement) {
    var number = numeral(inputElement.value);
    inputElement.value = number.format('0,0');
}

function number(inputElement) {
    var number = numeral(inputElement.value);
    inputElement.value = number.value();
}