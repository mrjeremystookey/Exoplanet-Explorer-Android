package r.stookey.exoplanetexplorer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.work.WorkManager
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Inject

class ExoplanetViewModel @Inject constructor(private val repo: RepositoryImpl,
                                            private val workManager: WorkManager) : ViewModel() {

          init {
              Timber.d("ExoplanetViewModel initialized")
          }

}