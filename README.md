# A Real-time data synchronization approach for high-availability micro applications
Documentation of the case study explained in the arcticle</br>

# CondSys

A system for managing a network of condominiums must, by default, include mechanisms and functionalities such as access control, property sales, exchanges and rentals, reservation of common areas for events, and more.

This is the core idea behind the CondSys condominium system—a system already known and monitored by the study group that authored this paper—focusing on a specific functionality: <b>Reserving a rentable area</b>.

The choice of this functionality is justified by the fact that it requires data from different microservices, separated from the monolithic version of CondSys, aiming to maintain the Single Responsibility Principle for each microservice. The documentations of the each microsservices are in your respective directories.

# Setup

After clone this repository, inside the folder of the cloned repository, you need to run the one that contains the necessary applications along with the microservices:

        docker compose -f 'compose.case-study.yml' up -d --build

For execution, it is recommended to have:
- Linux kerner 6.0 + | Windows 10 or higher | MacOs Sequoia 15.4+
- Docker 26.0+ 
- 8GB of RAM