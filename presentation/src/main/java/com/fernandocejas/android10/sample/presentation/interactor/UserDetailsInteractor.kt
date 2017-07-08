package com.fernandocejas.android10.sample.presentation.interactor

import com.fernandocejas.android10.sample.domain.User
import com.fernandocejas.android10.sample.domain.interactor.GetUserDetails
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity
import com.fernandocejas.android10.sample.presentation.mapper.UserModelDataMapper
import com.fernandocejas.android10.sample.presentation.model.UserModel
import io.reactivex.Observer
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

@PerActivity
class UserDetailsInteractor @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetails,
    private val userModelDataMapper: UserModelDataMapper
) {

  fun execute(observer: Observer<UserModel>, params: GetUserDetails.Params) {
    val o = object : DisposableObserver<User>() {
      override fun onComplete() {
        observer.onComplete()
      }

      override fun onError(e: Throwable) {
        observer.onError(e)
      }

      override fun onNext(user: User) {
        observer.onNext(userModelDataMapper.transform(user))
      }
    }
    getUserDetailsUseCase.execute(o, params)
  }

  fun dispose() {
    getUserDetailsUseCase.dispose()
  }
}
