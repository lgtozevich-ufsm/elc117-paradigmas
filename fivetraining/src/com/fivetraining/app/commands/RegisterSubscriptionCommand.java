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

public class RegisterSubscriptionCommand extends ConsoleCommand {
    private final SubscriptionDAO subscriptionDAO;
    private final UserDAO userDAO;

    public RegisterSubscriptionCommand(SubscriptionDAO subscriptionDAO, UserDAO userDAO) {
        this.subscriptionDAO = subscriptionDAO;
        this.userDAO = userDAO;

        addParameter(ConsoleParameter.createString("cpf aluno", true));
        addParameter(ConsoleParameter.createString("codigo plano", true));
        addParameter(ConsoleParameter.createDate("data inicio", true));
        addParameter(ConsoleParameter.createDate("data fim", true));
        addParameter(ConsoleParameter.createString("numero cartao", true));
        addParameter(ConsoleParameter.createDate("expiracao cartao", true));
        addParameter(ConsoleParameter.createString("cvc cartao", true));
    }

    @Override
    public String getName() {
        return "insc-aluno";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        String cpf = interaction.getArgument("cpf aluno").asString();

        try {

            User user = userDAO.findByCpf(cpf);

            if (user == null) {
                throw new ConsoleCommandExecutionException("Nenhum usuário com o cpf \"" + cpf + "\" foi encontrado");
            }
            int userId = user.getId();

            int planId = Integer.parseInt(interaction.getArgument("codigo plano").asString());
            LocalDate startDate = interaction.getArgument("data inicio").asDate();
            LocalDate endDate = interaction.getArgument("data fim").asDate();
            String cardNumber = interaction.getArgument("numero cartao").asString();
            LocalDate cardExpiration = interaction.getArgument("expiracao cartao").asDate();
            String cardCvc = interaction.getArgument("cvc cartao").asString();

            CreditCard creditCard = new CreditCard();
            creditCard.setNumber(cardNumber);
            creditCard.setExpiryDate(cardExpiration.toString());
            creditCard.setCvv(cardCvc);

            Subscription subscription = new Subscription();
            subscription.setUserId(userId);
            subscription.setPlanId(planId);
            subscription.setStartDate(Date.valueOf(startDate));
            subscription.setEndDate(Date.valueOf(endDate));
            subscription.setCreditCard(creditCard);


            subscriptionDAO.insert(subscription);

            interaction.getConsole().writeLine("A inscrição foi registrada com sucesso.");
            interaction.getConsole().writeLine("- ID do aluno: " + subscription.getUserId());
            interaction.getConsole().writeLine("- ID do plano: " + subscription.getPlanId());
            interaction.getConsole().writeLine("- Data de início: " + subscription.getStartDate());
            interaction.getConsole().writeLine("- Data de fim: " + subscription.getEndDate());
            interaction.getConsole().writeLine("- Número do cartão: " + subscription.getCreditCard().getNumber());
            interaction.getConsole().writeLine("- Expiração do cartão: " + subscription.getCreditCard().getExpiryDate());
            interaction.getConsole().writeLine("- CVC do cartão: " + subscription.getCreditCard().getCvv());


        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}