package com.finanControl.ai_agente.tools;

import com.finanControl.ai_agente.entity.ExpenseEntity;
import com.finanControl.ai_agente.entity.UserEntity;
import com.finanControl.ai_agente.enums.TypeExpense;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class AssistantTools {

    @Tool("Busca de dados do usuario por ID")
    public List<ExpenseEntity> findUserData(UUID userId) {
        return List.of(
                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 2)).name("Padaria")
                        .typeExpense(TypeExpense.FOOD).price(new BigDecimal("18.50")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 3)).name("Uber")
                        .typeExpense(TypeExpense.TRANSPORT).price(new BigDecimal("27.90")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 4)).name("Aluguel")
                        .typeExpense(TypeExpense.HOUSING).price(new BigDecimal("1300.00")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 5)).name("Farmácia")
                        .typeExpense(TypeExpense.HEALTH).price(new BigDecimal("64.30")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 6)).name("Curso Java")
                        .typeExpense(TypeExpense.EDUCATION).price(new BigDecimal("149.90")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 7)).name("Cinema")
                        .typeExpense(TypeExpense.ENTERTAINMENT).price(new BigDecimal("42.00")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 8)).name("Roupas")
                        .typeExpense(TypeExpense.SHOPPING).price(new BigDecimal("289.99")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 9)).name("Conta de água")
                        .typeExpense(TypeExpense.UTILITIES).price(new BigDecimal("98.40")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 10)).name("Netflix")
                        .typeExpense(TypeExpense.SUBSCRIPTION).price(new BigDecimal("39.90")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 11)).name("Presente")
                        .typeExpense(TypeExpense.OTHER).price(new BigDecimal("120.00")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 12)).name("Almoço fora")
                        .typeExpense(TypeExpense.FOOD).price(new BigDecimal("58.90")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 13)).name("Gasolina")
                        .typeExpense(TypeExpense.TRANSPORT).price(new BigDecimal("160.00")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 14)).name("Condomínio")
                        .typeExpense(TypeExpense.HOUSING).price(new BigDecimal("450.00")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 15)).name("Dentista")
                        .typeExpense(TypeExpense.HEALTH).price(new BigDecimal("220.00")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 16)).name("Livro técnico")
                        .typeExpense(TypeExpense.EDUCATION).price(new BigDecimal("89.90")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 17)).name("Streaming filme")
                        .typeExpense(TypeExpense.ENTERTAINMENT).price(new BigDecimal("19.90")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 18)).name("Tênis")
                        .typeExpense(TypeExpense.SHOPPING).price(new BigDecimal("399.00")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 19)).name("Internet")
                        .typeExpense(TypeExpense.UTILITIES).price(new BigDecimal("119.90")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 20)).name("Spotify")
                        .typeExpense(TypeExpense.SUBSCRIPTION).price(new BigDecimal("21.90")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 21)).name("Doação")
                        .typeExpense(TypeExpense.OTHER).price(new BigDecimal("50.00")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 22)).name("Mercado")
                        .typeExpense(TypeExpense.FOOD).price(new BigDecimal("312.45")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 23)).name("Ônibus")
                        .typeExpense(TypeExpense.TRANSPORT).price(new BigDecimal("9.40")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 24)).name("Manutenção casa")
                        .typeExpense(TypeExpense.HOUSING).price(new BigDecimal("280.00")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 25)).name("Exames")
                        .typeExpense(TypeExpense.HEALTH).price(new BigDecimal("340.00")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 26)).name("Workshop")
                        .typeExpense(TypeExpense.EDUCATION).price(new BigDecimal("199.00")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 27)).name("Show")
                        .typeExpense(TypeExpense.ENTERTAINMENT).price(new BigDecimal("180.00")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 28)).name("Eletrônicos")
                        .typeExpense(TypeExpense.SHOPPING).price(new BigDecimal("899.90")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 29)).name("Conta de luz")
                        .typeExpense(TypeExpense.UTILITIES).price(new BigDecimal("210.70")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 30)).name("Academia")
                        .typeExpense(TypeExpense.SUBSCRIPTION).price(new BigDecimal("99.90")).build(),

                ExpenseEntity.builder().id(UUID.randomUUID()).user(new UserEntity())
                        .date(LocalDate.of(2025, 1, 31)).name("Imprevisto")
                        .typeExpense(TypeExpense.OTHER).price(new BigDecimal("75.00")).build()
        );
    }

}
