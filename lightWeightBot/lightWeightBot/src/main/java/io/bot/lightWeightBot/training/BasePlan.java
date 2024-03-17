package io.bot.lightWeightBot.training;

import org.springframework.context.annotation.Configuration;

public class BasePlan {
    private final StringBuilder training = new StringBuilder();

    private final int workingWeight;

    public BasePlan(int maxWeight){
        workingWeight = ((int) (maxWeight * 0.8) + 2) / 5 * 5;
    }
    public String calculatorWarmUp(){
        training.setLength(0);
        training.append("""
                Разминка:
                1) 20 кг на 20 повторений
                2)""").append(" " + ((int) (workingWeight * 0.5) + 2) / 5 * 5).append(" кг на 10 повторений\n").append("3) ").append(((int) (workingWeight * 0.8) + 2) / 5 * 5).append(" кг на 6 повторений\n").append("4) ").append(((int) (workingWeight * 0.9) + 2) / 5 * 5).append(" кг на 4 повторения\n");
        return training.toString();
    }
    public String calculatorTraining(){
        training.setLength(0);
        training.append("""
                Перейдем к тренировке.
                Между подходами отдыхаем от 3 до 5 минут!
                Здесь ты должен строго фиксировать свое количество повторений в первом подходе, чтобы наблюдать за прогрессом. Если их количество стало 12 раз и больше, то пробуй пожать новый максимум!
                Итак, приступим. Все подходы делаем до отказа (пока не сможем пожать):
                """);
        training.append("1) ").append(workingWeight).append("\n");
        int newWeight = ((int) (workingWeight * 0.9) + 2) / 5 * 5;
        training.append("2) ").append(newWeight).append("\n");
        newWeight = ((int) (workingWeight * 0.7) + 2) / 5 * 5;
        training.append("3) ").append(newWeight).append("\n");
        return training.toString();
    }
    public String calculatorDumbbells(){
        training.setLength(0);
        int weight = ((int) (workingWeight * 0.25) + 2) / 2 * 2;
        training.append("Затем по плану у нас жим (одна гантеля - ").append(weight).append(") гантелей на наклонной скамье (30-45°)\n");
        training.append("1) Делаем на максимум\n").append("2) 8 повторений\n").append("3) 6 повторений\n");
        return training.toString();
    }
    public String calculatorBars(){
        training.setLength(0);
        training.append("В самом конце делаем брусья, либо на тренажере, либо на обычных. Все делаем со своим весом или чуть больше. 3 подхода по 10 раз.");
        return training.toString();
    }
}
