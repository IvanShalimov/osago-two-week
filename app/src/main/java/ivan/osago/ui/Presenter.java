package ivan.osago.ui;

/**
 * Created by ivan on 15.10.16.
 */
public interface Presenter {

    int CALCULATE_CLICK = 0;
    int REQUEST_CLICK = 1;
    int SELECT_FIRST_DATE = 2;
    int SELECT_SECOND_DATE = 3;
    int CHECK_DATE = 4;

    void onDestroy();

    void onClick(int type);
}
