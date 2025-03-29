let checkIcon = document.querySelectorAll('.check');
let getBgImage = document.querySelector('.bg');
let bgImage = {
  check1: '/assets/images/images1.jpeg',
  check2: '/assets/images/images2.jpeg',
  check3: '/assets/images/images3.jpeg',
  check4: '/assets/images/images4.jpeg',
}
let navPopup = document.querySelector('.menu-popup');
let content = document.querySelector('.content');

let currentCheckPosition = 1;
let index = 0;
setInterval(() => {
   if(index < 4) {
      index++;
      let res = 'check' + index;
      console.log('path', bgImage[res]);
      getBgImage.setAttribute('src', bgImage[res]);
      console.log(getBgImage.getAttribute('src'));
      getBgImage.style.transition = 'all 0.8s';    
      if(index != 4) {
      checkIcon[index].style.background = "deepskyblue";
      }
      for(let j = 0; j < 4; j++) {
        if(j < index) {
          checkIcon[j].style.background = "transparent";
        }
      }
   }
 
  if(index === 4) {
     checkIcon[3].style.background = "transparent";
     index= 0;
     checkIcon[index].style.background = "deepskyblue";
   }
},2500);

// MENU BUTTON
let menuicons = document.querySelectorAll('.menu-icon');
let rotateBtn = document.querySelector('.menu-btn');
rotateBtn.addEventListener('click', (event) => {
  event.preventDefault();
  if(navPopup.style.display === 'block') {
      content.style.display = 'block';
      navPopup.style.display = 'none';
    }
    else {
      navPopup.style.display = 'block';
      content.style.display = 'none';
    }
    for (let i = 0; i < menuicons.length; i++) {
      if(menuicons[i].getAttribute('data-clicked') === "no") {
      menuicons[i].setAttribute('data-clicked', 'yes');
      }
      else {
        menuicons[i].setAttribute('data-clicked', 'no');

        menuicons[i].style.color = 'black';
      }
    }
});

