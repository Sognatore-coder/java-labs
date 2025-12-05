package common;

public enum MessageType {
    CONNECT,          // Подключение с никнеймом
    DISCONNECT,       // Отключение
    BROADCAST,        // Широковещательное сообщение
    PRIVATE,          // Личное сообщение
    USER_LIST,        // Запрос списка пользователей
    USER_LIST_RESPONSE // Ответ со списком пользователей
}
