let toggleBtn = document.querySelector('.toggle-btn');
let tic = document.querySelector('.a');
let tic2 = document.querySelector('.a1');
let i = 0;
let check1 = document.getElementById('check1');
function slideToggle() {
  if(i%2 === 0) {
    tic.style.transition = 'all 0.8s';
    check1.checked = true;
    tic.classList.add('slideToggle');
    i++;
  }
  else {
     check1.checked = false;
     tic.classList.remove('slideToggle');
     i++;
  }
}
let j = 0;
let check2 = document.getElementById('check2');
function slideToggle2() {
  if (j % 2 === 0) {
    check2.checked = true;
    tic2.classList.add('slideToggle');
    j++;
  }
  else {
    check2.checked = false;
    tic2.classList.remove('slideToggle');
    j++;
}
}
    let myoptions = document.querySelectorAll('.myoptions');
    updatePlaceholderCounter();
function updatePlaceholderCounter() {
    for(let i = 0; i < myoptions.length; i++) {
        myoptions[i].setAttribute('placeholder', `Option ${i+1}`)
    }
}