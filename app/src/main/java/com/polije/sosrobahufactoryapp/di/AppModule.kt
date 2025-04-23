package com.polije.sosrobahufactoryapp.di

import com.polije.sosrobahufactoryapp.data.repository.PabrikRepositoryImpl
import com.polije.sosrobahufactoryapp.domain.repository.pabrik.PabrikRepository
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.DashboardUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.DetailPesananMasukUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.GetItemRestockUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.InsertRestockUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.LoginUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.PesananMasukUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.RiwayatRestokUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.TokenUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.UpdatePesananUseCase
import com.polije.sosrobahufactoryapp.ui.factory.home.HomePabrikViewModel
import com.polije.sosrobahufactoryapp.ui.factory.home.produkTerlaris.TopProductPabrikViewModel
import com.polije.sosrobahufactoryapp.ui.factory.login.FactoryLoginViewModel
import com.polije.sosrobahufactoryapp.ui.factory.pesanan.PesananPabrikViewModel
import com.polije.sosrobahufactoryapp.ui.factory.pesanan.detailPesanan.DetailPesananPabrikViewModel
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.RiwayatPabrikViewModel
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.pilihProdukRestok.ProdukRestokPabrikViewModel
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.tambahRestok.TambahRestokPabrikViewModel
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
    factoryOf(::UpdatePesananUseCase)

    viewModelOf(::FactoryLoginViewModel)
    viewModelOf(::HomePabrikViewModel)
    viewModelOf(::PesananPabrikViewModel)
    viewModelOf(::RiwayatPabrikViewModel)
    viewModelOf(::DetailPesananPabrikViewModel)
    viewModelOf(::ProdukRestokPabrikViewModel)
    viewModelOf(::TambahRestokPabrikViewModel)
    viewModelOf(::TopProductPabrikViewModel)

}