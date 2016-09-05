package jp.co.ntv.app.gacha.view;

import jp.co.ntv.app.gacha.R;
import jp.co.ntv.app.gacha.activity.ArchiveActivity;
import jp.co.ntv.app.gacha.activity.ArchiveListActivity;
import jp.co.ntv.app.gacha.activity.ArchiveMapActivity;
import jp.co.ntv.app.gacha.activity.Gacha10Activity;
import jp.co.ntv.app.gacha.activity.HowToActivity;
import jp.co.ntv.app.gacha.activity.KaigaiArchiveActivity;
import jp.co.ntv.app.gacha.activity.NewsViewActivity;
import jp.co.ntv.app.gacha.activity.TopActivity;
import jp.co.ntv.app.gacha.media.MediaChangeListener;
import jp.co.ntv.app.gacha.model.Constants;
import jp.co.ntv.app.gacha.model.DataManager;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

@SuppressWarnings("unused")
public class MenuUtil {
    private final Activity act;

    public MenuUtil(Activity act) {
        this.act = act;
    }

    public boolean action(int id) {
        Intent intent = null;
        switch (id) {
        case R.id.top:
            
            // NhutLT : change to "Gacha10Activity.class" 
            // to transit to gacha10 screen             
            intent = new Intent(act.getApplicationContext(), Gacha10Activity.class);
            break;
        case R.id.darts:
            if ((act instanceof ArchiveListActivity && ((ArchiveListActivity) act)
                    .isDarts())
                    || act instanceof ArchiveMapActivity) {
                return false;

            }
            intent = new Intent(act.getApplicationContext(),
                    ArchiveActivity.class);
            intent.putExtra(Constants.EX_IS_DARTS_OA_LIST, true);
            break;
        case R.id.kaigai:
            if (act instanceof KaigaiArchiveActivity) {
                return false;
            }
            intent = new Intent(act.getApplicationContext(),
                    KaigaiArchiveActivity.class);
            break;
        case R.id.news:
            if (act instanceof NewsViewActivity) {
                return true;
            }
            intent = new Intent(act.getApplicationContext(), NewsViewActivity.class);
            break;            
        case R.id.howto:
            if (act instanceof HowToActivity) {
                return true;
            }
            intent = new Intent(act.getApplicationContext(), HowToActivity.class);
            break;
        case R.id.volume:
            DataManager.setAllowSound(!DataManager.isAllowSound());

            if (act instanceof MediaChangeListener) {
                ((MediaChangeListener) act).onChangeAllowSound(DataManager
                        .isAllowSound());
            }
            
            return true;
        }

        if (intent != null) {
            act.startActivity(intent);
            return true;
        }
        return false;
    }

    public void inflate(Menu menu) {
        MenuInflater inflater = act.getMenuInflater();

        inflater.inflate(R.menu.menu, menu);
        // setMenuBackground(Color.WHITE);
    }

    public void prepare(Menu menu) {
        MenuItem volume = (MenuItem) menu.findItem(R.id.volume);
        if (DataManager.isAllowSound())
            volume.setIcon(R.drawable.menu_icon_volume);
        else
            volume.setIcon(R.drawable.menu_icon_volume2);
    }

    protected void setMenuBackground(final int color) {
        /*
         * final LayoutInflater f = act.getLayoutInflater(); final Handler
         * handler = new Handler(); act.getLayoutInflater().setFactory( new
         * Factory() {
         * 
         * @Override public View onCreateView ( String name, Context context,
         * AttributeSet attrs ) { if ( name.equalsIgnoreCase(
         * "com.android.internal.view.menu.IconMenuItemView" ) ) { try { // Ask
         * our inflater to create the view
         * 
         * ClassLoader cl = act.getClassLoader(); Class<?> clazz; clazz =
         * cl.loadClass("com.android.internal.view.menu.IconMenuView");
         * Constructor<?> constructor = clazz.getConstructor(Context.class,
         * AttributeSet.class); Object view = constructor.newInstance(context,
         * attrs); ViewGroup vg = (ViewGroup) view; // �ｽ�ｽ�ｽ�ｽ�ｽﾅ色�ｽw�ｽ�ｽ //
         * vg.setBackgroundColor(R.color.hoge); �ｽ�ｽ�ｽﾆゑｿｽ�ｽﾜゑｿｽ�ｽ�ｽ�ｽ�ｽ�ｽﾈゑｿｽ�ｽ�ｽ�ｽ�ｽ
         * //vg.setBackgroundColor(Color.CYAN); �ｽ�ｽ�ｽ�ｽﾍゑｿｽ�ｽﾜゑｿｽ�ｽ�ｽ�ｽ�ｽ
         * vg.setBackgroundColor(color);
         * //vg.setBackgroundResource(R.drawable.hoge); �ｽﾅ画像�ｽ�ｽw�ｽi�ｽﾉゑｿｽ�ｽ驍ｱ�ｽﾆゑｿｽ�ｽﾂ能
         * 
         * return vg; } catch ( InflateException e ) {Log.d("Test",
         * "InflateException "); } catch ( ClassNotFoundException e )
         * {Log.d("Test", "ClassNotFoundException ");} catch (SecurityException
         * e) { // TODO �ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ黷ｽ catch �ｽu�ｽ�ｽ�ｽb�ｽN e.printStackTrace(); } catch
         * (NoSuchMethodException e) { // TODO �ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ黷ｽ catch �ｽu�ｽ�ｽ�ｽb�ｽN
         * e.printStackTrace(); } catch (IllegalArgumentException e) { // TODO
         * �ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ黷ｽ catch �ｽu�ｽ�ｽ�ｽb�ｽN e.printStackTrace(); } catch
         * (InstantiationException e) { // TODO �ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ黷ｽ catch �ｽu�ｽ�ｽ�ｽb�ｽN
         * e.printStackTrace(); } catch (IllegalAccessException e) { // TODO
         * �ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ黷ｽ catch �ｽu�ｽ�ｽ�ｽb�ｽN e.printStackTrace(); } catch
         * (InvocationTargetException e) { // TODO �ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ黷ｽ catch �ｽu�ｽ�ｽ�ｽb�ｽN
         * e.printStackTrace(); } } else {
         * 
         * } return null; } });
         */
    }

}
