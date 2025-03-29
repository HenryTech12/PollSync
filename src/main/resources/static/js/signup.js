let pwdicon = document.getElementById('pwdVisibility');
let inputPwd = document.getElementById('pwd');
let defaultPwdIconText = 'visibility';

pwdicon.addEventListener('click',() => {
    if(pwdicon.innerText !== defaultPwdIconText) {
      pwdicon.innerText = defaultPwdIconText;
      inputPwd.type = 'password';
    }
    else {
      inputPwd.type = 'text';
      pwdicon.innerText = 'visibility_off';
    }
});

let btnicon = document.getElementById('loading');
let signupBtn = document.getElementById('signup');
signupBtn.addEventListener('click',() => {
  btnicon.classList.add('move');
  setTimeout(() => {
    btnicon.innerText = 'progress_activity';
    btnicon.classList.replace('move','rotate');
    btnicon.transform = 'translateX(0px)';
    signupBtn.classList.add('space');
  },800);
});