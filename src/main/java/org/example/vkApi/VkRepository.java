package org.example.vkApi;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserFull;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.List;

public class VkRepository {
    private final int APP_ID;
    private final String CODE;
    private final VkApiClient vk;
    private final UserActor actor;


    public VkRepository() {
        Dotenv dotenv = Dotenv.configure().directory("Data/.env").load();

        this.APP_ID = Integer.parseInt(dotenv.get("APP_ID"));
        this.CODE = dotenv.get("CODE");

        TransportClient transportClient = new HttpTransportClient();
        vk = new VkApiClient(transportClient);
        actor = new UserActor((long) APP_ID, CODE);
    }

    public UserFull searchUserInfoByName(String name) {
        String[] nameParts = name.split(" ");
        String lastName = nameParts[0];
        String firstName = nameParts[1];

        try {
            Thread.sleep(1000); // Задержка 1 секунда
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<UserFull> users = null;

        try {
            users = vk.users().search(actor)
                    .q(firstName + " " + lastName)
                    .count(1)
                    .fields(Fields.SEX, Fields.BDATE, Fields.CITY, Fields.STATUS)
                    .execute()
                    .getItems();
        } catch (ApiException | ClientException e) {
            return null;
        }

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }

    public void printUserInfo(UserFull user) {
        if (user == null) {
            System.out.println("Пользователь не найден");
            return;
        }

        String sex = (user.getSex() != null) ? (user.getSex() == com.vk.api.sdk.objects.base.Sex.FEMALE ? "Женский" : "Мужской") : "Не указан";
        System.out.println("Пол: " + sex);

        String bdate = (user.getBdate() != null) ? user.getBdate() : "Не указана";
        System.out.println("Дата рождения: " + bdate);

        String city = (user.getCity() != null) ? user.getCity().getTitle() : "Не указан";
        System.out.println("Город: " + city);

        String status = (user.getStatus() != null) ? user.getStatus() : "Не указан";
        System.out.println("Статус: " + status);
    }
}
