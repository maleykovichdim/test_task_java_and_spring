
let URL = "http://localhost:8080/";
let URL_SAVE_USER = URL+"user";
let URL_UPDATE_USER = URL+"user_update";
let URL_GET_USER = URL+"user?name=";

let jName = document.querySelector('#name');
let jSelect= document.querySelector('#select');
let jCheckbox = document.querySelector('#checkbox');
let jSaveButton = document.querySelector('#button_save');
let jChangeModeButton = document.querySelector('#button_change_mode');
let jLoadButton = document.querySelector('#button_load_mode');
let jDiv = document.querySelector('#main-div');
let jDiv2 = document.querySelector('#main-div2');
let jLabelMode = document.querySelector('#mode');


jSaveButton.addEventListener('click', buttonSaveFunction);
jChangeModeButton.addEventListener('click', buttonChangeModeFunction);
jLoadButton.addEventListener('click', buttonLoadFunction)

async function buttonChangeModeFunction(e) {
  e.preventDefault();
  if (jLabelMode.textContent == "Mode: ADD NEW user"){
    jLabelMode.textContent = "Mode: UPDATE"
    if (localStorage.getItem('test_task_last_user_name') !== null){
      jName.value= localStorage.getItem('test_task_last_user_name');
    }
  }else{
    jLabelMode.textContent = "Mode: ADD NEW user"
  }
}


async function buttonSaveFunction(e) {
  e.preventDefault();
  if (jLabelMode.textContent == "Mode: ADD NEW user"){
    await storeUserFunction(URL_SAVE_USER);
  }else{
    await storeUserFunction(URL_UPDATE_USER);
  }
}

async function storeUserFunction(url) {

  let name = jName.value;
  if (!checkName(name)) return;
  let selected = getSelectedSectors();
  if (!checkSelectSectors(selected)) return;

  var myJson = {};
  myJson['name'] = name;
  if (jCheckbox.checked) {
    myJson['agreeTerms'] = 1;
  }else{
    myJson['agreeTerms'] = 0;
  }

  myJson['sectors'] = [];
  for (let a of selected){
    myJson.sectors.push(a);
  }

  const response = await fetch(url, {
    method: 'POST',
    headers: {
      'Content-type': 'application/json'
    },
    body: JSON.stringify(myJson)
  });

  let text = await response.text();
  if (!response.ok) {
    showAlert('SERVER REQUEST ERROR: '+text, 'alert alert-danger');
    return
  }else{
    showAlert('SENT. '+text, 'alert alert-success');
    jName.value = '';
    jCheckbox.checked = false;
    for (var option of jSelect.options) {
      option.selected = false;
    }
    localStorage.setItem('test_task_last_user_name', name);
  }
}


async function buttonLoadFunction(e) {
  e.preventDefault();
  let name = jName.value;
  if (!checkName(name)) return;

  const response = await fetch(URL_GET_USER+name);

  let resJson = await response.json();
  if (!response.ok) {
    showAlert('SERVER USER DATA REQUEST ERROR. Check NAME. ', 'alert alert-danger');
    return;
  }else {
    if (resJson.agreeTerms === true){
      jCheckbox.checked = true;
    }else{
      jCheckbox.checked = false;
    }
    for (var option of jSelect.options) {
      option.selected = false;
    }
    let values = resJson.sectors;
    for (let value of values){
      for (var option of jSelect.options) {
         if (option.value == value.sectorValue){
           option.selected = true;
         }
      }
    }
    showAlert('Done. User data is received. ', 'alert alert-success');
  }
}


// Show alert message
function showAlert(message, className) {
  this.clearAlert();

  // Create div
  const div_ = document.createElement('div');
  // Add classes
  div_.className = className;
  // Add text
  div_.appendChild(document.createTextNode(message));
  jDiv.insertBefore(div_, jDiv2);

  // Timeout
  setTimeout(() => {
    this.clearAlert();
  }, 3000);
}

// Clear alert message
function clearAlert() {
  const currentAlert = document.querySelector('.alert');

  if(currentAlert) {
    currentAlert.remove();
  }
}


 function checkName(name){
   let regName = /^[a-zA-Z]+ [a-zA-Z]+$/;
   let regName2 = /^[a-zA-Z]+/;
   if (!regName.test(name) && !regName2.test(name)) {
     showAlert('Invalid name given. ', 'alert alert-danger');
     return false;
   }
   if (name.length < 1 || name === ' ' || name === '') {
     showAlert('Please, type your name ', 'alert alert-danger');
     return false;
   }
   return true;
 }


 function getSelectedSectors(){
   var selected = [];
   for (var option of jSelect.options) {
     if (option.selected) {
       selected.push(option.value);
     }
   }
   return selected;
 }
 function checkSelectSectors(selected){
   if (selected.length < 1) {
     showAlert('Please, choose sectors ', 'alert alert-danger');
     return false;
   }
  return true;
 }