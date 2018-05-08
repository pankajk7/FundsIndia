package fundsindia.pankaj.fundsindia.helper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class ObserverAdapter<T> implements Observer<T> {
    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onSubscribe(Disposable d) {

    }
}