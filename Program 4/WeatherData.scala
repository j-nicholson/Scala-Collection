/**
  * Class that holds data related to the weather in Pennsylvania in August
  * @param wban Wban Station Number
  * @param year_month_day Year Month and Day
  * @param t_max Maximum temperature on given day
  * @param t_min Minimum temperature on given day
  * @param t_avg Average temperature on given day
  * @param station Wban Station Name
  * @param location Wban Station Location
  */
class WeatherData(val wban: Int, val year_month_day: Int, val t_max: Int, val t_min: Int, val t_avg: Int, val station: String, val location: String) {}
