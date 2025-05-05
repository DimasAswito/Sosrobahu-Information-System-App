package com.polije.sosrobahufactoryapp.di

import com.polije.sosrobahufactoryapp.data.repository.AgenRepositoryImpl
import com.polije.sosrobahufactoryapp.data.repository.DistributorRepositoryImpl
import com.polije.sosrobahufactoryapp.data.repository.PabrikRepositoryImpl
import com.polije.sosrobahufactoryapp.domain.repository.agen.AgenRepository
import com.polije.sosrobahufactoryapp.domain.repository.distributor.DistributorRepository
import com.polije.sosrobahufactoryapp.domain.repository.pabrik.PabrikRepository
import com.polije.sosrobahufactoryapp.domain.usecase.agen.LoginAgenUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.agen.UserSessionAgenUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.DasbhoardDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.DetailPesananMasukDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.LoginDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.LogoutDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.OrderDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.PesananMasukDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.PilihProdukPabrikDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.RiwayatOrderDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.UserSessionDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.DashboardPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.DetailPesananMasukPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.GetItemRestockPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.InsertRestockPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.LoginPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.LogoutPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.PesananMasukPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.RiwayatRestokPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.UpdatePesananPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.UserSessionPabrikUseCase
import com.polije.sosrobahufactoryapp.ui.agen.home.HomeAgenViewModel
import com.polije.sosrobahufactoryapp.ui.agen.login.AgenLoginViewModel
import com.polije.sosrobahufactoryapp.ui.distributor.home.HomeDistributorViewModel
import com.polije.sosrobahufactoryapp.ui.distributor.login.DistributorLoginViewModel
import com.polije.sosrobahufactoryapp.ui.distributor.order.OrderDistributorViewModel
import com.polije.sosrobahufactoryapp.ui.distributor.order.detailOrder.DetailOrderDistributorViewModel
import com.polije.sosrobahufactoryapp.ui.distributor.order.pilihProdukDistributor.PilihProdukDistributorViewModel
import com.polije.sosrobahufactoryapp.ui.distributor.order.tambahOrder.TambahOrderDistributorViewModel
import com.polije.sosrobahufactoryapp.ui.distributor.pesanan.PesananDistributorViewModel
import com.polije.sosrobahufactoryapp.ui.distributor.pesanan.detailPesanan.DetailPesananDistributorViewModel
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
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    factoryOf(::PabrikRepositoryImpl) { bind<PabrikRepository>() }
    factoryOf(::DistributorRepositoryImpl) { bind<DistributorRepository>() }
    factoryOf(::AgenRepositoryImpl) { bind<AgenRepository>() }

    factoryOf(::LoginPabrikUseCase)
    factoryOf(::DashboardPabrikUseCase)
    factoryOf(::PesananMasukPabrikUseCase)
    factoryOf(::RiwayatRestokPabrikUseCase)
    factoryOf(::DetailPesananMasukPabrikUseCase)
    factoryOf(::GetItemRestockPabrikUseCase)
    factoryOf(::InsertRestockPabrikUseCase)
    factoryOf(::UpdatePesananPabrikUseCase)
    factoryOf(::UserSessionPabrikUseCase)
    factoryOf(::LogoutPabrikUseCase)

    factoryOf(::UserSessionDistributorUseCase)
    factoryOf(::LoginDistributorUseCase)
    factoryOf(::LogoutDistributorUseCase)
    factoryOf(::DasbhoardDistributorUseCase)
    factoryOf(::PesananMasukDistributorUseCase)
    factoryOf(::RiwayatOrderDistributorUseCase)
    factoryOf(::DetailPesananMasukDistributorUseCase)
    factoryOf(::PilihProdukPabrikDistributorUseCase)
    factoryOf(::OrderDistributorUseCase)

    factoryOf(::LoginAgenUseCase)
    factoryOf(::UserSessionAgenUseCase)

    viewModelOf(::HomePabrikViewModel)
    viewModelOf(::FactoryLoginViewModel)
    viewModelOf(::PesananPabrikViewModel)
    viewModelOf(::RiwayatPabrikViewModel)
    viewModelOf(::DetailPesananPabrikViewModel)
    viewModelOf(::ProdukRestokPabrikViewModel)
    viewModelOf(::TambahRestokPabrikViewModel)
    viewModelOf(::TopProductPabrikViewModel)

    viewModelOf(::DistributorLoginViewModel)
    viewModelOf(::HomeDistributorViewModel)
    viewModelOf(::PesananDistributorViewModel)
    viewModelOf(::OrderDistributorViewModel)
    viewModelOf(::DetailPesananDistributorViewModel)
    viewModelOf(::PilihProdukDistributorViewModel)
    viewModelOf(::TambahOrderDistributorViewModel)


    viewModelOf(::AgenLoginViewModel)

    viewModelOf(::AgenLoginViewModel)
    viewModelOf(::HomeAgenViewModel)
    viewModelOf(::AgenLoginViewModel)

}