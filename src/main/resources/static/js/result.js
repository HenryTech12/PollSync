let colors = ['green', 'blue', 'yellow', 'red'];

let allIndicatos = document.querySelectorAll('.box');


if(allIndicatos.length > 0) {
    for(let i= 0; i < allIndicatos.length; i++) {
        allIndicatos[i].style.background = colors[i];
    }
}

/*SHOW RESULTS */
let values = document.querySelectorAll('.value');
let designs = document.querySelectorAll('.bar');
let totalVotes = 0;
let color = 'red';


for(let i = 0; i < values.length; i++) {
  totalVotes += Number.parseInt(values[i].textContent);
}
for(let i = 0; i < designs.length; i++) {
   
 
   const percent = Math.floor(calculateVotePercentage(values[i].textContent, Number.parseInt(totalVotes)));
   
   if(percent <= 30) {
     color = 'red';
   }
   else if(percent <= 50) {
     color = 'yellow';
   }
   else if(percent < 70) {
     color = 'deepskyblue';
   }
   else if(percent >= 70) {
     color = 'green';
   }
  
   designs[i].style.width = `${percent}%`;
   designs[i].style.background = `${color}`;
}

function calculateVotePercentage(vote, totalVotes) {
   let percent = (vote/totalVotes) * 100;
   return percent;
}