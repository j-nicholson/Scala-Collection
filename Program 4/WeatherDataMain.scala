import scala.io.Source
import scala.collection.mutable.ListBuffer

object WeatherDataMain {

  /**
    * Formats an integer of form [yyyyMMdd] to form of [August dd, YYYY]
    * @param date the integer date to be converted
    * @return August dd, YYYY
    */
  def dateConversion(date: Int): String = {
    val date_string = date.toString
    val date_string_year = date_string.substring(0, 4)
    var date_string_month = date_string.substring(4, 6)
    val date_string_day = date_string.substring(6, 8)
    if (date_string_month == "08") {
      date_string_month = "August"
    }

    return date_string_month + " " + date_string_day + ", " + date_string_year
  }

  /**
    * Main routine to run Weather Data program
    * @param args
    */
  def main(args: Array[String]): Unit = {
    val weather_info: ListBuffer[WeatherData] = ListBuffer()
    var running = true

    while(running) {
      println("Please enter the name of the weather file to be processed: ")
      val input = scala.io.StdIn.readLine()
      if((!input.contains("tempData2015")) && (!input.contains("tempData2017"))) {
        println("Please enter a valid weather data file.")
      }
      else {
        val data = Source.fromFile(input).getLines().toArray

        for(i <- data.indices) {
          val split_data = data(i).split(" ")
          val location_replace = split_data(6).replace("_", " ")
          weather_info.append(new WeatherData(split_data(0).toInt, split_data(1).toInt, split_data(2).toInt, split_data(3).toInt, split_data(4).toInt, split_data(5), location_replace))
        }

        // Use variables
        var year = 0
        if(input == "tempData2015.txt") {
          year = 2015
        }
        else {
          year = 2017
        }
        var location = ""
        var date = 0
        var station = ""
        var convert_date = ""
        val number_of_stations = weather_info.length / 31

        // Find the maximum temperature
        printf("\n1. What is the maximum temperature reported by any of the WBAN's during August %d?\n", year)
        var maximum = 0

        for(i <- weather_info.indices) {
          if (weather_info(i).t_max > maximum) {
            maximum = weather_info(i).t_max
            location = weather_info(i).location
            date = weather_info(i).year_month_day
            convert_date = dateConversion(date)
            station = weather_info(i).station
          }
        }

        printf("The max temperature recorded in August %d was %d on %s at %s in %s.\n", year, maximum, convert_date, location, station)

        // Find the minimum temperature
        printf("\n2. What is the minimum temperature reported by any of the WBAN's during August %d?\n", year)
        var minimum = 100000000

        for(i <- weather_info.indices) {
          if (weather_info(i).t_min < minimum) {
            minimum = weather_info(i).t_min
            location = weather_info(i).location
            date = weather_info(i).year_month_day
            convert_date = dateConversion(date)
            station = weather_info(i).station
          }
        }

        printf("The minimum temperature recorded in August %d was %d on %s at %s in %s.\n", year, minimum, convert_date, location, station)

        // Find the average temperature
        printf("\n3. What is the average for all 25 reporting stations in August %d?\n", year)
        var average = 0
        var count = 0
        var total = 0

        for(i <- weather_info.indices) {
          count += 1
          total += weather_info(i).t_avg
        }

        average = total / count
        printf("The average temperature recorded in August %d was %d.\n", year, average)

        // Find the hottest day
        printf("\n4. What was the hottest day in Pennsylvania in August %d?\n", year)
        var current_maximum_station = 1
        var total_t_max = 0
        var t_max_average = 0
        var t_max_date = 0
        var t_max_station = ""
        var t_max_location = ""

        val sorted_weather_info = weather_info sortBy(_.year_month_day)

        for(i <- sorted_weather_info.indices) {
          total_t_max += sorted_weather_info(i).t_max
          if(current_maximum_station == number_of_stations) {
            if((total_t_max / number_of_stations) > t_max_average) {
              t_max_average = total_t_max / number_of_stations
              t_max_date = sorted_weather_info(i).year_month_day
              convert_date = dateConversion(t_max_date)
              t_max_station = sorted_weather_info(i).station
              t_max_location = sorted_weather_info(i).location
            }
            current_maximum_station = 0
            total_t_max = 0
          }
          current_maximum_station += 1
        }

        printf("The hottest day in Pennsylvania in August %d was %d on %s at %s in %s.\n", year, t_max_average, convert_date, t_max_location, t_max_station)

        // Find the coldest day
        printf("\n5. What was the coldest day in Pennsylvania in August %d?\n", year)
        var current_minimum_station = 1
        var total_t_min = 0
        var t_min_average = 100000000
        var t_min_date = 0
        var t_min_station = ""
        var t_min_location = ""

        for(i <- sorted_weather_info.indices) {
          total_t_min += sorted_weather_info(i).t_min
          if(current_minimum_station == number_of_stations) {
            if((total_t_min / number_of_stations) < t_min_average) {
              t_min_average = total_t_min / number_of_stations
              t_min_date = sorted_weather_info(i).year_month_day
              convert_date = dateConversion(t_min_date)
              t_min_station = sorted_weather_info(i).station
              t_min_location = sorted_weather_info(i).location
            }
            current_minimum_station = 0
            total_t_min = 0
          }
          current_minimum_station += 1
        }

        printf("The coldest day in Pennsylvania in August %d was %d on %s at %s in %s.\n", year, t_min_average, convert_date, t_min_location, t_min_station)

        running = false
      }
    }
  }
}