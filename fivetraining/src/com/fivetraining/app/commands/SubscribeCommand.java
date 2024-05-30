package com.fivetraining.app.commands;

import com.fivetraining.app.daos.SubscriptionDAO;
import com.fivetraining.app.daos.UserDAO;
import com.fivetraining.app.models.CreditCard;
import com.fivetraining.app.models.Subscription;
import com.fivetraining.app.models.User;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class SubscribeCommand extends ConsoleCommand {
    private final SubscriptionDAO subscriptionDAO;
    private final UserDAO userDAO;

    public SubscribeCommand(SubscriptionDAO subscriptionDAO, UserDAO userDAO) {
        this.subscriptionDAO = subscriptionDAO;
        this.userDAO = userDAO;

        addParameter(ConsoleParameter.createString("cpf", true));
        addParameter(ConsoleParameter.createInteger("código do plano", true));
        addParameter(ConsoleParameter.createDate("data de início", true));
        addParameter(ConsoleParameter.createDate("data de fim", true));
        addParameter(ConsoleParameter.createString("número do cartão", true));
        addParameter(ConsoleParameter.createDate("data de expiração do cartão", true));
        addParameter(ConsoleParameter.createString("cvc do cartão", true));
    }

    @Override
    public String getName() {
        return "insc-aluno";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        String cpf = interaction.getArgument("cpf").asString();
        int planId = interaction.getArgument("código do plano").asInteger();
        LocalDate startDate = interaction.getArgument("data de início").asDate();
        LocalDate endDate = interaction.getArgument("data de fim").asDate();
        String cardNumber = interaction.getArgument("número do cartão").asString();
        LocalDate cardExpiration = interaction.getArgument("data de expiração do cartão").asDate();
        String cardCvv = interaction.getArgument("cvc do cartão").asString();

        try {
            User user = userDAO.findByCpf(cpf);

            if (user == null) {
                throw new ConsoleCommandExecutionException("Nenhum usuário com o cpf \"" + cpf + "\" foi encontrado");
            }

            CreditCard creditCard = new CreditCard();
            creditCard.setNumber(cardNumber);
            creditCard.setExpiryDate(cardExpiration.toString());
            creditCard.setCvv(cardCvv);

            Subscription subscription = new Subscription();
            subscription.setUserId(user.getId());
            subscription.setPlanId(planId);
            subscription.setStartDate(Date.valueOf(startDate));
            subscription.setEndDate(Date.valueOf(endDate));
            subscription.setCreditCard(creditCard);

            subscriptionDAO.insert(subscription);

            interaction.getConsole().writeLine("A inscrição foi registrada com sucesso.");
            interaction.getConsole().writeLine();
            interaction.getConsole().writeLine("o  id: " + subscription.getId());
            interaction.getConsole().writeLine("|  id do aluno: " + subscription.getUserId());
            interaction.getConsole().writeLine("|  id do plano: " + subscription.getPlanId());
            interaction.getConsole().writeLine("|  data de início: " + subscription.getStartDate());
            interaction.getConsole().writeLine("|  data de fim: " + subscription.getEndDate());
            interaction.getConsole().writeLine("|  número do cartão: " + subscription.getCreditCard().getNumber());
            interaction.getConsole().writeLine("|  data de expiração do cartão: " + subscription.getCreditCard().getExpiryDate());
            interaction.getConsole().writeLine("`- cvc do cartão: " + subscription.getCreditCard().getCvv());
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}