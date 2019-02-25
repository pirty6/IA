var readline = require('readline');
var rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout,
  terminal: false
});

var arr[], i = 0;
rl.on('line', function(line){
    arr[i] = line;
    i++;
})

console.log(arr[0] + arr[1]);
