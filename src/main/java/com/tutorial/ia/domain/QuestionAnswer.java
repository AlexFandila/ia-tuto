package com.tutorial.ia.domain;

public class QuestionAnswer {
    private String question;
    private String answer;

    public QuestionAnswer(String question, String answer) {
        this.question = question;
         this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    // NO HACEN FALTA SETTERS PORQUE SIEMPRE QUE PREGUNTA Y RESPUESTA ESTÁN DEFINIDAS NO DEBERÍAN CAMBIAR
    // PRINCIPIO DE INMUTABILIDAD
}
