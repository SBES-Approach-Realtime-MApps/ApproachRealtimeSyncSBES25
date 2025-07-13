# A Real-time data synchronization approach for high-availability micro applications

This artifact presents a proof of concept to evaluate the applicability, strengths, and weaknesses of the RTDSync approach proposed in the paper "A Real-time data synchronization approach for high-availability micro applications". The subject application for this empirical analysis is a condominium management system, referred to as CondSys.

# CondSys

CondSys is a system that enables condominium management by a single condominium administrator.

A condominium is defined as a legal entity composed of units, which may include houses or apartments, as well as specific areas, which may include garages, and common areas, which may include rentable areas, courts, playgrounds, and swimming pools, among others.

From an administrative perspective, each condominium must have a manager, who is responsible for the interests of the residents and ensuring the proper functioning and maintenance of the condominium. This manager may be a resident elected in an assembly or a professional hired for that purpose.

Regarding the residences, each unit must have only one responsible resident, who may be the unit owner or a tenant.
These residents must inform other residents (e.g., children and spouses) of the unit so that access to areas of the condominium can be facilitated.

Concerning the utilization of shared spaces, the CondSys system must enable the rental of common areas by residents for designated periods.

To do so, the condominium manager must establish a reservation period for the common areas (e.g., 30, 60, or 90 days).
A reservation must contain specific information about the common area being requested, the resident who is making the request, and the area itself. 

Due to limitations in scope, the remaining system functionalities are not addressed in this artifact repository.

This is the core idea behind the CondSys condominium system—a system already known and monitored by the study group that authored this paper—focusing on a specific functionality: <b>Reserving a rentable area</b>.

The choice of this functionality is justified by the fact that it requires data from different microservices, separated from the monolithic version of CondSys, aiming to maintain the Single Responsibility Principle for each microservice. The documentations of the each microsservices are in your respective directories.</br>

[MSCondominium Docs](MSCondominium/README.md)</br>
[MSReservation Docs](MSReservation/README.md)</br>
[MSResident Docs](MSResident/README.md)</br>
[MSReplicator Docs](MSReplicator/README.md)

# Setup

After clone this repository, inside the folder of the cloned repository, you need to run the one that contains the necessary applications along with the microservices:

        docker compose -f 'compose.case-study.yml' up -d --build

Some times, its possible the MSReservation can be disconnected of the MariaDB because the CPU use is very high, in this case, try an especific command line for reactivate this container

        docker start MSReservation

For continues the tests for our approuch, see the [Test Instance](docs/example.md)

For execution, it is recommended to have:
- Linux kerner 6.0 + | Windows 10 or higher | MacOs Sequoia 15.4 +
- Docker 26.0 + 
- 8GB of RAM
