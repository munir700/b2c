package co.yap.modules.kyc.amendments.missinginfo

object KycAmendmentFieldsMappingHelper {
    private val map: HashMap<String, String> = hashMapOf()

    init {
        // KYC
        map["IDNumber"] = "EID number"
        map["FirstName"] = "First name"
        map["MiddleName"] = "Middle name"
        map["LastName"] = "Last name"
        map["DateofBirth"] = "Date of birth"
        map["Nationality"] = "Nationality"
        map["IDExpireyDateNumber"] = "EID expiry"
        map["Gender"] = "Gender"

        // POB
        map["CountryofBirth"] = "Birth country"
        map["CityofBirth"] = "Birth city"
        map["DualNationality"] = "Dual nationality"
        map["NationalityAsPerEID"] = "Nationality"
        map["SecondCountry"] = "Second country"

        // TAX
        map["CountryofTaxResidence"] = "Tax residence country"
        map["TINNumber"] = "TIN number"
        map["SecondCountryofTaxResidence"] = "Second residence country"

        // EMPLOYMENT
        map["EmploymentStatus"] = "Employment status"
        map["EmploymentName"] = "Employer name"
        map["MonthlySalary"] = "Monthly salary"
        map["CashDeposit"] = "Cash deposit"
        map["CompanyName"] = "Company name"
        map["IndustrySegment"] = "Industry segment"
        map["CompanyNameListWhereCompanyDoesBusiness"] = "Company business list"
    }

    fun getValueForKey(key: String): String {
        return map[key] ?: ""
    }
}