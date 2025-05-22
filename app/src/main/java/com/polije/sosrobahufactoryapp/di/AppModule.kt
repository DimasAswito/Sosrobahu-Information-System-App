package com.polije.sosrobahufactoryapp.di

import com.polije.sosrobahufactoryapp.data.repository.AgenRepositoryImpl
import com.polije.sosrobahufactoryapp.data.repository.DistributorRepositoryImpl
import com.polije.sosrobahufactoryapp.data.repository.PabrikRepositoryImpl
import com.polije.sosrobahufactoryapp.data.repository.SalesRepositoryImpl
import com.polije.sosrobahufactoryapp.domain.repository.agen.AgenRepository
import com.polije.sosrobahufactoryapp.domain.repository.distributor.DistributorRepository
import com.polije.sosrobahufactoryapp.domain.repository.pabrik.PabrikRepository
import com.polije.sosrobahufactoryapp.domain.repository.sales.SalesRepository
import com.polije.sosrobahufactoryapp.domain.usecase.agen.DashboardAgenUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.agen.DetailPesananMasukUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.agen.DownloadNotaAgenUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.agen.LogOutAgenUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.agen.LoginAgenUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.agen.OrderAgenUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.agen.PesananMasukAgenUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.agen.PilihBarangDistributorAgenUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.agen.RiwayatOrderAgenUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.agen.UpdateStatusPesananAgenUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.agen.UserSessionAgenUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.DasbhoardDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.DetailPesananMasukDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.DownloadNotaDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.LoginDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.LogoutDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.OrderDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.PesananMasukDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.PilihProdukPabrikDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.RiwayatOrderDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.UpdateStatusPesananDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.UserSessionDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.DashboardPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.DetailPesananMasukPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.DownloadNotaUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.GetItemRestockPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.InsertRestockPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.LoginPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.LogoutPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.PesananMasukPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.RiwayatRestokPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.UpdatePesananPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.UserSessionPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.sales.DashboardSalesUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.sales.DeleteTokoUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.sales.DownloadNotaSalesUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.sales.KunjunganTokoUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.sales.LogOutSalesUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.sales.LoginSalesUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.sales.OrderSalesUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.sales.PilihBarangAgenSalesUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.sales.RiwayatOrderSalesUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.sales.TambahKunjunganTokoUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.sales.TambahTokoUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.sales.TokoSalesUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.sales.UpdateTokoUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.sales.UserSessionSalesUseCase
import com.polije.sosrobahufactoryapp.ui.agen.home.HomeAgenViewModel
import com.polije.sosrobahufactoryapp.ui.agen.login.AgenLoginViewModel
import com.polije.sosrobahufactoryapp.ui.agen.order.OrderAgenViewModel
import com.polije.sosrobahufactoryapp.ui.agen.order.detailOrder.DetailOrderAgenViewModel
import com.polije.sosrobahufactoryapp.ui.agen.order.pilihProdukAgen.PilihProdukAgenViewModel
import com.polije.sosrobahufactoryapp.ui.agen.order.tambahOrder.TambahOrderAgenViewModel
import com.polije.sosrobahufactoryapp.ui.agen.pesanan.PesananAgenViewModel
import com.polije.sosrobahufactoryapp.ui.agen.pesanan.detailPesanan.DetailPesananAgenViewModel
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
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.detailRestok.DetailRestokPabrikViewModel
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.pilihProdukRestok.ProdukRestokPabrikViewModel
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.tambahRestok.TambahRestokPabrikViewModel
import com.polije.sosrobahufactoryapp.ui.sales.daftarToko.DaftarTokoSalesViewModel
import com.polije.sosrobahufactoryapp.ui.sales.daftarToko.component.tambahKunjungan.BottomSheetTambahKunjunganViewModel
import com.polije.sosrobahufactoryapp.ui.sales.daftarToko.detailDaftarToko.DetailDaftarTokoSalesViewModel
import com.polije.sosrobahufactoryapp.ui.sales.daftarToko.listRiwayatKunjungan.ListRiwayatKunjunganViewModel
import com.polije.sosrobahufactoryapp.ui.sales.daftarToko.tambahToko.TambahTokoViewModel
import com.polije.sosrobahufactoryapp.ui.sales.home.HomeSalesViewModel
import com.polije.sosrobahufactoryapp.ui.sales.login.SalesLoginViewModel
import com.polije.sosrobahufactoryapp.ui.sales.order.OrderSalesViewModel
import com.polije.sosrobahufactoryapp.ui.sales.order.detailOrder.DetailOrderSalesViewModel
import com.polije.sosrobahufactoryapp.ui.sales.order.pilihProdukSales.PilihProdukSalesViewModel
import com.polije.sosrobahufactoryapp.ui.sales.order.tambahOrder.TambahOrderSalesViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    factoryOf(::PabrikRepositoryImpl) { bind<PabrikRepository>() }
    factoryOf(::DistributorRepositoryImpl) { bind<DistributorRepository>() }
    factoryOf(::AgenRepositoryImpl) { bind<AgenRepository>() }
    factoryOf(::SalesRepositoryImpl) { bind<SalesRepository>() }

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
    factoryOf(::DownloadNotaUseCase)

    factoryOf(::UserSessionDistributorUseCase)
    factoryOf(::LoginDistributorUseCase)
    factoryOf(::LogoutDistributorUseCase)
    factoryOf(::DasbhoardDistributorUseCase)
    factoryOf(::PesananMasukDistributorUseCase)
    factoryOf(::RiwayatOrderDistributorUseCase)
    factoryOf(::DetailPesananMasukDistributorUseCase)
    factoryOf(::PilihProdukPabrikDistributorUseCase)
    factoryOf(::OrderDistributorUseCase)
    factoryOf(::UpdateStatusPesananDistributorUseCase)
    factoryOf(::DownloadNotaDistributorUseCase)

    factoryOf(::LoginAgenUseCase)
    factoryOf(::UserSessionAgenUseCase)
    factoryOf(::DashboardAgenUseCase)
    factoryOf(::LogOutAgenUseCase)
    factoryOf(::PesananMasukAgenUseCase)
    factoryOf(::DetailPesananMasukUseCase)
    factoryOf(::UpdateStatusPesananAgenUseCase)
    factoryOf(::RiwayatOrderAgenUseCase)
    factoryOf(::PilihBarangDistributorAgenUseCase)
    factoryOf(::OrderAgenUseCase)
    factoryOf(::DownloadNotaAgenUseCase)

    factoryOf(::LoginSalesUseCase)
    factoryOf(::LogOutSalesUseCase)
    factoryOf(::UserSessionSalesUseCase)
    factoryOf(::DashboardSalesUseCase)
    factoryOf(::TokoSalesUseCase)
    factoryOf(::RiwayatOrderSalesUseCase)
    factoryOf(::PilihBarangAgenSalesUseCase)
    factoryOf(::OrderSalesUseCase)
    factoryOf(::TambahTokoUseCase)
    factoryOf(::UpdateTokoUseCase)
    factoryOf(::DeleteTokoUseCase)
    factoryOf(::KunjunganTokoUseCase)
    factoryOf(::DownloadNotaSalesUseCase)
    factoryOf(::TambahKunjunganTokoUseCase)
    factoryOf(::DeleteTokoUseCase)

    viewModelOf(::HomePabrikViewModel)
    viewModelOf(::FactoryLoginViewModel)
    viewModelOf(::PesananPabrikViewModel)
    viewModelOf(::RiwayatPabrikViewModel)
    viewModelOf(::DetailPesananPabrikViewModel)
    viewModelOf(::ProdukRestokPabrikViewModel)
    viewModelOf(::TambahRestokPabrikViewModel)
    viewModelOf(::TopProductPabrikViewModel)
    viewModelOf(::DetailRestokPabrikViewModel)

    viewModelOf(::DistributorLoginViewModel)
    viewModelOf(::HomeDistributorViewModel)
    viewModelOf(::PesananDistributorViewModel)
    viewModelOf(::OrderDistributorViewModel)
    viewModelOf(::DetailPesananDistributorViewModel)
    viewModelOf(::PilihProdukDistributorViewModel)
    viewModelOf(::TambahOrderDistributorViewModel)
    viewModelOf(::DetailOrderDistributorViewModel)

    viewModelOf(::AgenLoginViewModel)
    viewModelOf(::HomeAgenViewModel)
    viewModelOf(::PesananAgenViewModel)
    viewModelOf(::DetailPesananAgenViewModel)
    viewModelOf(::OrderAgenViewModel)
    viewModelOf(::PilihProdukAgenViewModel)
    viewModelOf(::TambahOrderAgenViewModel)
    viewModelOf(::DetailOrderAgenViewModel)

    viewModelOf(::SalesLoginViewModel)
    viewModelOf(::HomeSalesViewModel)
    viewModelOf(::DaftarTokoSalesViewModel)
    viewModelOf(::OrderSalesViewModel)
    viewModelOf(::PilihProdukSalesViewModel)
    viewModelOf(::TambahOrderSalesViewModel)
    viewModelOf(::TambahTokoViewModel)
    viewModelOf(::DetailDaftarTokoSalesViewModel)
    viewModelOf(::ListRiwayatKunjunganViewModel)
    viewModelOf(::DetailOrderSalesViewModel)
    viewModelOf(::BottomSheetTambahKunjunganViewModel)

}