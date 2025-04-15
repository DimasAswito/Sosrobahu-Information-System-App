package com.polije.sosrobahufactoryapp.di

import com.polije.sosrobahufactoryapp.data.pabrik.repository.PabrikRepositoryImpl
import com.polije.sosrobahufactoryapp.domain.pabrik.repositiory.PabrikRepository
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.DashboardUseCase
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.DetailPesananMasukUseCase
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.LoginUseCase
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.PesananMasukUseCase
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.RiwayatRestokUseCase
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.TokenUseCase
import com.polije.sosrobahufactoryapp.ui.factory.home.HomeViewModel
import com.polije.sosrobahufactoryapp.ui.factory.login.FactoryLoginViewModel
import com.polije.sosrobahufactoryapp.ui.factory.pesanan.PesananViewModel
import com.polije.sosrobahufactoryapp.ui.factory.pesanan.detailPesanan.DetailPesananViewModel
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.RiwayatViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::PabrikRepositoryImpl){bind<PabrikRepository>() }

    factoryOf(::LoginUseCase)
    factoryOf(::TokenUseCase)
    factoryOf(::DashboardUseCase)
    factoryOf(::PesananMasukUseCase)
    factoryOf(::RiwayatRestokUseCase)
    factoryOf(::DetailPesananMasukUseCase)

    viewModelOf(::FactoryLoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::PesananViewModel)
    viewModelOf(::RiwayatViewModel)
    viewModelOf(::DetailPesananViewModel)

}