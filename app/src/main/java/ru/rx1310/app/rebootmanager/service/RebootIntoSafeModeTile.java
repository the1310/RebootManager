// ! rx1310 <rx1310@inbox.ru> | Copyright (c) rx1310, 2020 | MIT License

package ru.rx1310.app.rebootmanager.service;

import android.graphics.drawable.Icon;

import android.os.Build;

import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import android.support.annotation.RequiresApi;

import eu.chainfire.libsuperuser.Shell;

import ru.rx1310.app.rebootmanager.R;
import ru.rx1310.app.rebootmanager.RebootManager;

@RequiresApi(api = Build.VERSION_CODES.N)
public class RebootIntoSafeModeTile extends TileService {

	@Override
	public void onStartListening() {

		Tile t = getQsTile();

		t.setIcon(Icon.createWithResource(this, R.drawable.ic_safe_mode));
		t.setLabel(getString(R.string.tile_reboot_into_safe_mode));

		if (Shell.SU.available()) {
			t.setState(Tile.STATE_ACTIVE);
		} else {
			t.setState(Tile.STATE_UNAVAILABLE);
		}

		t.updateTile();
		
		super.onStartListening();

	}

	// обработка нажатия
	@Override
	public void onClick() {

		// проверка root
		if (Shell.SU.available()) {
			// выполнение команды
			Shell.SU.run(RebootManager.CMD_SAFE_MODE);
		} else {
			// сообщение об ошибке
			RebootManager.showToast(getString(R.string.msg_root_not_available), this);
		}

	}

}


