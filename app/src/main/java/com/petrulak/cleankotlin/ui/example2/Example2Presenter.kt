package com.petrulak.cleankotlin.ui.example2

import com.petrulak.cleankotlin.di.scope.ViewScope
import com.petrulak.cleankotlin.domain.interactor.GetWeatherRemotelyUseCase
import com.petrulak.cleankotlin.domain.model.Weather
import com.petrulak.cleankotlin.ui.example2.Example2Contract.View
import java.util.*
import javax.inject.Inject


@ViewScope
class Example2Presenter
@Inject
constructor(private val getWeatherRemotelyUseCase: GetWeatherRemotelyUseCase) : Example2Contract.Presenter {

    private var view: View? = null

    override fun attachView(view: View) {
        this.view = checkNotNull(view)
    }

    override fun start() {
        refresh()
    }

    override fun stop() {
        getWeatherRemotelyUseCase.dispose()
    }

    override fun refresh() {
        getWeatherRemotelyUseCase.execute({ onWeatherSuccess(it) }, { onWeatherError(it) }, "London,uk")
    }

    private fun onWeatherSuccess(item: Weather) {
        val modified = item.copy(visibility = Random().nextInt(80 - 65) + 65)
        view?.showWeather(modified)
    }

    private fun onWeatherError(t: Throwable) {
        //no need to log this exception, it is already logged on the Interactor level.
    }
}