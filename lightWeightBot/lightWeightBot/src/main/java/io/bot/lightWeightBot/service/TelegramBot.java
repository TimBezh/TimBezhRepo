package io.bot.lightWeightBot.service;
import io.bot.lightWeightBot.training.BasePlan;
import io.bot.lightWeightBot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;
    public TelegramBot(BotConfig config){
        this.config = config;
    }
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatID = update.getMessage().getChatId();
            System.out.println(messageText);
            if(messageText.startsWith("/")) {
                switch (messageText) {
                    case "/start":
                        startCommandReceived(chatID, update.getMessage().getChat().getFirstName());
                        break;
                    default:
                        sendMessage(chatID, "Сука, сидит ломает программу, иди занимайся");
                        break;
                }
            }
            else {
                try {
                    int weightMax = Integer.parseInt(messageText);
                    if(weightMax < 50) sendMessage(chatID, "Иди лучше отожмись, рано тебе пока");
                    else if(weightMax > 300){
                        sendMessage(chatID, "Ага блять, так я и поверил.");
                    }
                    else {
                        BasePlan basePlan = new BasePlan(weightMax);
                        sendMessage(chatID, basePlan.calculatorWarmUp());
                        sendMessage(chatID, basePlan.calculatorTraining());
                        sendMessage(chatID, basePlan.calculatorDumbbells());
                        sendMessage(chatID, basePlan.calculatorBars());
                        sendMessage(chatID, "В целом, это все. Грудь и трицепс больше нагружать не стоит (даже если не чувствуешь усталость). Советую отдохнуть около 3 дней)");
                    }
                } catch (NumberFormatException e) {
                    sendMessage(chatID, "Ты че нахуй, числа вводить не умеешь");
                };
            }
        }
    }
    private void startCommandReceived(long chatID, String name){
        String s = """
                Добро пожаловать на ебейшую программу тренировок по жиму лежа\s
                Данная тренировка направлена на рост силовых и мышц.
                Правила крайне просты:\s
                1) Я пишу - ты выполняешь!\s
                2) Если я не отвечаю - не спамить, иначе я тебя сообщениями захуярю\s
                3) Следи за питанием, не ешь хуйню\s
                4) Не сказал "Light Weight"- пожал меньше\s
                5) Сделай разминку перед треней\s
                6) ДОЛЖЕН БЫТЬ СТРАХУЮЩИЙ!!!\s
                7) Сразу говорить об ошибках и багах\s
                Удачи на тренировке и хороших результатов)
                """;
        String answer =
                "Привет, " + name + "!\n" + s;
        sendMessage(chatID, answer);
        sendMessage(chatID, "А теперь, скинь свой максимум на раз (число в кг)");
    }
    private void sendMessage(long chatID, String answerText){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatID));
        message.setText(answerText);
        try {
            execute(message);
        } catch (TelegramApiException error){
            throw  new RuntimeException(error);
        }
    }
}
