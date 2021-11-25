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
        map["FrontImage"] = "Front side of EID"
        map["BackImage"] = "Back side of EID"

        // POB
        map["CountryofBirth"] = "Birth country"
        map["CityofBirth"] = "Birth city"
        map["DualNationality"] = "Dual nationality"
        map["NationalityAsPerEID"] = "Nationality"
        map["SecondCountry"] = "Second country"

        // TAX
        map["CountryofTaxResidence"] = "Tax residence country"
        map["TINNumber1"] = "TIN number 1"
        map["SecondCountryofTaxResidence"] = "Second residence country"
        map["TINNumber2"] = "TIN number 2"

        // EMPLOYMENT
        map["EmploymentStatus"] = "Employment status"
        map["EmploymentName"] = "Employer name"
        map["MonthlySalary"] = "Monthly salary"
        map["CashDeposit"] = "Cash deposit"
        map["CompanyName"] = "Company name"
        map["IndustrySegment"] = "Industry segment"
        map["CompanyNameListWhereCompanyDoesBusiness"] = "Company business list"
        map["Country2"] = "Second country"
    }

    fun getValueForKey(key: String): String {
        return map[key] ?: ""
    }
}