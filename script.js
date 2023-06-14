let currentQuestionIndex = 0;
let score = 0;
let correct = false;
let answer = '';

let questions = [];
let correctAnswers = [];
let incorrectAnswers = [];

const fileSelector = document.getElementById('file-selector');
let selectedFile = document.getElementById('file-selector').value;

fileSelector.addEventListener('change', (event) => {
  const selectedFile = event.target.value;
  console.log('Selected file:', selectedFile);
  resetArrays();
  loadQuestions(selectedFile);
});

function loadQuestions(selectedFile) {
  const xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function() {
    if (xhr.readyState === XMLHttpRequest.DONE) {
      if (xhr.status === 200) {
        const lines = xhr.responseText.split('\n');
        shuffleArray(lines);
        for (const line of lines) {
          const parts = line.split('\t');
          const question = parts[0];
          const correctAnswer = parts[1];
          questions.push(question);
          correctAnswers.push(correctAnswer);
          incorrectAnswers = generateIncorrectAnswers(correctAnswer, 3);
        }
        console.log('Loading questions from file:', selectedFile);
        
        displayQuestion(currentQuestionIndex);
      } else {
        console.error('Failed to load questions');
      }
    }
  };
  xhr.open('GET', selectedFile, true);
  xhr.send();
}

function resetArrays() {
  questions = [];
  correctAnswers = [];
  incorrectAnswers = [];
}

function displayQuestion(index) {
    const questionElement = document.getElementById('question');
    const answersElement = document.getElementById('answers');
    const currentQuestion = questions[index];
    const currentCorrectAnswer = correctAnswers[index];
    const currentIncorrectAnswers = generateIncorrectAnswers(currentCorrectAnswer, 3);
  
    questionElement.textContent = currentQuestion;
    answersElement.innerHTML = '';
    console.log(currentCorrectAnswer)
    const allAnswers = [currentCorrectAnswer, ...currentIncorrectAnswers];
    shuffleArray(allAnswers);
  
    for (let i = 0; i < allAnswers.length; i++) {
      const li = document.createElement('li');
      const radio = document.createElement('input');
      radio.type = 'radio';
      radio.name = 'answer';
      radio.value = i + 1;
      li.appendChild(radio);
  
      const span = document.createElement('span');
      span.textContent = allAnswers[i];
      span.addEventListener('click', () => {
        radio.checked = true;
          showResult();
          showNextQuestion()
      });
      li.appendChild(span);
  
      answersElement.appendChild(li);
    }
  }

function generateIncorrectAnswers(correctAnswer, count) {
    const maxNumber = correctAnswer.length;
    const incorrectAnswers = [];
    let i = 0;
    let uniqueAnswers = [];
    while (uniqueAnswers.length < count) {
      let incorrectAnswerIndex = Math.floor(Math.random() * maxNumber);
      let incorrectAnswer = correctAnswers[incorrectAnswerIndex];
      if (!uniqueAnswers.includes(incorrectAnswer) && !correctAnswer.includes(incorrectAnswer)) {
        incorrectAnswers.push(incorrectAnswer);
      }
    const filteredAnswers = incorrectAnswers.filter(answer => answer !== undefined);
    uniqueAnswers = [...new Set(filteredAnswers)];
    i++;
    if (i >= maxNumber) {
      break;
    }
    }
    return uniqueAnswers;
  }

  function shuffleArray(array) {
    for (let i = array.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      const temp = array[i];
      array[i] = array[j];
      array[j] = temp;
    }
  }

function selectAnswer() {
    const selectedAnswer = document.querySelector('input[name="answer"]:checked');
    if (selectedAnswer) {
      const userAnswer = selectedAnswer.nextSibling.textContent.trim();
      const correctAnswer = correctAnswers[currentQuestionIndex];
      answer = correctAnswer;
      
      if (correctAnswer.includes(userAnswer)) {        
          score++;
          correct = true;
      } else{
        correct = false;
      }
    }
  }
  
function showResult() {
  const resultText = `${answer}`;
  const resultElement = document.getElementById('result');
  resultElement.innerHTML = resultText;
  if (correct) {
    resultElement.classList.remove('incorrect');
    resultElement.classList.add('correct');
} else {
      resultElement.classList.remove('correct');
    resultElement.classList.add('incorrect');
  }
}

function showNextQuestion() {
  selectAnswer();
  currentQuestionIndex++;
  displayQuestion(currentQuestionIndex);
  showResult();
}
document.getElementById('next').addEventListener('click', showNextQuestion);
loadQuestions(selectedFile);