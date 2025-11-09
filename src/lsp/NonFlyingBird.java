package lsp;

public abstract class NonFlyingBird extends Bird {
    // У нелетающих птиц нет метода fly(), поэтому добавим специфичный метод swim
    public abstract void swim();
}
