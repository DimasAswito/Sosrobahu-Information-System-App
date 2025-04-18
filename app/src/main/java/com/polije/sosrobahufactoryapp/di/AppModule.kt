package com.polije.sosrobahufactoryapp.di

import com.polije.sosrobahufactoryapp.data.pabrik.repository.PabrikRepositoryImpl
import com.polije.sosrobahufactoryapp.domain.pabrik.repositiory.PabrikRepository
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.DashboardUseCase
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.DetailPesananMasukUseCase
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.GetItemRestockUseCase
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.InsertRestockUseCase
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.LoginUseCase
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.PesananMasukUseCase
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.RiwayatRestokUseCase
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.TokenUseCase
import com.polije.sosrobahufactoryapp.ui.factory.home.HomeViewModel
import com.polije.sosrobahufactoryapp.ui.factory.home.produkTerlaris.TopProductViewModel
import com.polije.sosrobahufactoryapp.ui.factory.login.FactoryLoginViewModel
import com.polije.sosrobahufactoryapp.ui.factory.pesanan.PesananViewModel
import com.polije.sosrobahufactoryapp.ui.factory.pesanan.detailPesanan.DetailPesananViewModel
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.RiwayatViewModel
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.pilihProdukRestok.ProdukRestokViewModel
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.tambahRestok.TambahRestokViewModel
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
    factoryOf(::GetItemRestockUseCase)
    factoryOf(::InsertRestockUseCase)

    viewModelOf(::FactoryLoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::PesananViewModel)
    viewModelOf(::RiwayatViewModel)
    viewModelOf(::DetailPesananViewModel)
    viewModelOf(::ProdukRestokViewModel)
    viewModelOf(::TambahRestokViewModel)
    viewModelOf(::TopProductViewModel)

}