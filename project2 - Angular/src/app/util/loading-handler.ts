import { BehaviorSubject, Observable, of } from "rxjs";
import { delay, switchMap } from "rxjs/operators";


export class LoadingHandler {
    private _isLoading = new BehaviorSubject(false);

    isLoading$: Observable<boolean> = this._isLoading.pipe(
        switchMap(isLoading => {
            if (!isLoading) {
                return of(false);
            }
            return of(true).pipe(delay(250)); //You don't want to immediately show loading when the data is going to come back in a second
        })
    );

    start() {
        this._isLoading.next(true);
    }

    finish() {
        this._isLoading.next(false);
    }

}