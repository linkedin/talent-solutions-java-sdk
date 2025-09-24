# LinkedIn-Talent-Solutions-SDK-Java-Client-Library

The LinkedIn Talent Solutions (LTS) Java SDK is the client library that enables seamless integration with LinkedIn's Talent APIs. The SDK offers specialized client implementations for different business use cases like Apply Connect, Job Posting, Pay-for-Performance (P4P), etc.

## **Understanding roles**

### **Partner**
Partners are organizations that have a direct integration agreement with LinkedIn. They act as intermediaries between LinkedIn and their customers (typically employers or recruiters). They perform the following functions:
- Provision and manage customer applications
- Act on behalf of their customers to post jobs
- Access aggregated reporting and analytics
- Manage billing and contracts for P4P services

### **Customer**
Customers are the end users of the partner's services - typically employers, recruiters, or companies looking to post jobs on LinkedIn. They perform the following functions:
- Post jobs on LinkedIn via the partner
- Receive applications from job applicants
- Access reports and analytics related to their job postings

## **Getting started**

### **Prerequisites**
* Java 1.8 or later

### Step 1: **Configure your project**

Insert dependency and repository code snippets into your project. Implement the snippets for Gradle or Maven projects as follows:

#### **For Gradle**
Update your `build.gradle` file with the following repository and dependency configurations:

```gradle
repositories {
    maven {
        url "https://linkedin.jfrog.io/artifactory/talent-solutions-java-sdk/"
    }
    mavenCentral()
}

dependencies {
    implementation "com.linkedin:talent-solutions-java-sdk:1.0.0-alpha"
}
```

#### **For Maven**
Update your `pom.xml` file with the following repository and dependency configurations:

```xml
<repositories>
    <repository>
        <id>public-jfrog</id>
        <url>https://linkedin.jfrog.io/artifactory/talent-solutions-java-sdk/</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.linkedin</groupId>
    <artifactId>talent-solutions-java-sdk</artifactId>
    <version>1.0.0-alpha</version>
</dependency>
```

> [!NOTE]
> If you are not using Gradle or Maven, you need to manually download JAR files from [Jfrog](https://linkedin.jfrog.io/ui/native/talent-solutions-java-sdk/com/linkedin/talent-solutions-java-sdk/) and add it to your project's classpath.

### **Step 2: Review SDK clients and API documentation**

The API operations are organized by business use case. As a LinkedIn partner, learn about the clients that align with your specific integration requirements.

#### **ProvisioningClient**
Manage customer onboarding by creating and configuring child developer applications. Refer to the [Provisioning API](https://learn.microsoft.com/en-us/linkedin/talent/apply-with-linkedin/provisioning-api?view=li-lts-2025-04) to learn more.

> [!IMPORTANT]
> The ProvisioningClient must be used first to create child developer applications, which generate the customer credentials (client_id and client_secret) required to authenticate and use all other API clients (JobPostingClient, P4PJobPostingClient, ApplyConnectJobPostingClient).

The API supports the following use cases:
* **[CreateApplication](https://learn.microsoft.com/en-us/linkedin/talent/apply-with-linkedin/provisioning-api?view=li-lts-2025-04#create-application)** - Create a child developer application
* **[UpdateApplication](https://learn.microsoft.com/en-us/linkedin/talent/apply-with-linkedin/provisioning-api?view=li-lts-2025-04#update-application)** - Update your existing child developer application
* **[GetApplication](https://learn.microsoft.com/en-us/linkedin/talent/apply-with-linkedin/provisioning-api?view=li-lts-2025-04#get-application)** - Retrieve a child developer application

#### **JobPostingClient**
Post and manage basic job listings on LinkedIn's platform. Refer to the [Job Posting API](https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/overview?view=li-lts-2025-04) to learn more.

The API supports the following use cases:
* **[ProcessJobPosting](https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/create-jobs?view=li-lts-2025-04)** - Create, update, upgrade, downgrade, close, and renew job posts
* **[GetTaskStatus](https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/task-status-api?view=li-lts-2025-04)** - Retrieve `LinkedInTaskStatus` returned in the API response
* **[GetJobPostingStatus](https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/get-job-status-api?view=li-lts-2025-04)** - Retrieve job post statuses using the `externalJobPostingId`

#### **P4PJobPostingClient**
Create pay-for-performance job campaigns with enhanced visibility and detailed performance analytics. Refer to the LinkedIn [P4P (Pay For Performance) Job Posting](https://learn.microsoft.com/en-us/linkedin/talent/p4p-job-posting?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04) and [Reports APIs](https://learn.microsoft.com/en-us/linkedin/talent/pay-for-performance/p4p-reports?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04) to learn about posting promoted jobs and retrieve reports of the P4P jobs, respectively.

The API supports the following use cases:
* **[ProvisionCustomerHiringContracts](https://learn.microsoft.com/en-us/linkedin/talent/job-postings/api/provision-customer-contracts?view=li-lts-2025-04)** - Create hiring contracts to provision P4P jobs to your customers
* **[GetP4PReportsByIds](https://learn.microsoft.com/en-us/linkedin/talent/pay-for-performance/p4p-reports?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04#get-report-by-ids)** - Retrieve P4P job posting performance reports using identifiers such as `externalJobPostingIds` or `partnerJobCode`
* **[GetP4PReportsByDate](https://learn.microsoft.com/en-us/linkedin/talent/pay-for-performance/p4p-reports?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04#get-report-by-date)** - Retrieve P4P job posting performance reports for a specific date range
* **[GetPartnerBudgetReports](https://learn.microsoft.com/en-us/linkedin/talent/pay-for-performance/p4p-reports?context=linkedin%2Ftalent%2Fpay-for-performance%2Ffull_context%2Fcontext&view=li-lts-2025-04#get-budget-report)** - Retrieve your budget reports

#### **ApplyConnectJobPostingClient**
Enable LinkedIn-hosted applications with customizable screening questions and real-time candidate notifications. Refer to the LinkedIn [Apply Connect Job Posting APIs](https://learn.microsoft.com/en-us/linkedin/talent/apply-connect/sync-jobs-onsite-apply?view=li-lts-2025-04) to learn more.

* **[SyncJobApplicationNotification](https://learn.microsoft.com/en-us/linkedin/talent/apply-connect/sync-job-application-notification?view=li-lts-2025-04)** - Deliver real-time feedback to applicants

### **Step 3: Implement the SDK**

The following examples demonstrate how to instantiate and use each SDK client. Each example includes the complete code needed to perform operations, from creating client instances to handling responses.

> [!IMPORTANT]
> Before using JobPostingClient, P4PJobPostingClient, or ApplyConnectJobPostingClient, you must first call the create operation of ProvisioningClient to generate your customer's `client_id` and `client_secret`. These credentials are required to authenticate and use all other API clients.

#### **ProvisioningClient**

```java
// Create ProvisioningClient instance using partner credentials
ProvisioningClient provisioningClient = LinkedInClientFactory.getInstance()
    .getProvisioningClient("partner-clientid","partner-clientsecret");

// Use ProvisioningClient to create customer applications
// This will generate customer credentials needed for other clients
```

#### **Basic JobPosting**

##### Create Basic JobPosting
```java
// Create JobPostingClient instance using customer credentials
JobPostingClient jobPostingClient = LinkedInClientFactory.getInstance()
    .getJobPostingClient("customer-clientid", "customer-clientsecret");

// Build JobPosting object
JobPosting jobPosting = JobPosting.builder()
    .externalJobPostingId("external job posting id")
    .companyId("companyId")
    .companyApplyUrl("https://careers.yourcompany.com")
    .jobPostingOperationType(JobPostingOperationType.CREATE)
    .title("Software Developer in Test")
    .description("Testing Job")
    .location("Sunnyvale, California")
    .listedAt(System.currentTimeMillis())
    .listingType(ListingType.BASIC)
    .employmentStatus(EmploymentStatus.FULL_TIME)
    .workplaceTypes(Arrays.asList(WorkplaceTypes.ON_SITE))
    .build();

// Build JobPostingRequest
JobPostingRequest jobPostingRequest = JobPostingRequest.builder()
    .elements(Arrays.asList(jobPosting))
    .build();

// Send JobPostingRequest
JobPostingResponse jobPostingResponse = jobPostingClient.post(jobPostingRequest);
```

##### Checking JobPosting Task Status
```java
JobTaskStatusResponse jobTaskStatusResponse = jobPostingClient
    .getTaskStatus(Arrays.asList("linkedin task urn"));
```

##### Checking Job Status
```java
JobPostingStatus jobPostingStatus = jobPostingClient
    .getJobPostingStatus(Arrays.asList("job posting id"));
```

#### **P4P JobPosting**

##### Create P4P JobPosting
```java
// Create P4PJobPostingClient instance using customer credentials
P4PJobPostingClient p4pJobPostingClient = LinkedInClientFactory.getInstance()
    .getP4PJobPostingClient("customer-clientid", "customer-clientsecret");

// Build P4PBudget instance for P4P JobPosting Request
P4PBudget p4PBudget = P4PBudget.builder()
    .payForPerformanceTotalBudget(MoneyAmount.builder()
        .amount("5.00")
        .currencyCode(CurrencyCode.USD)
        .build())
    .build();

JobPosting jobPosting = JobPosting.builder()
    .externalJobPostingId("external job posting id")
    .companyId("companyId")
    .companyApplyUrl("https://careers.yourcompany.com")
    .contract("urn:li:contract:{your-contract_id}")
    .companyJobCode("ATS-source-requisition_id_or_externalJobPostingId")
    .jobPostingOperationType(JobPostingOperationType.CREATE)
    .title("Software Developer in Test")
    .description("Testing Job")
    .location("Sunnyvale, California")
    .listedAt(System.currentTimeMillis())
    .listingType(ListingType.BASIC)
    .employmentStatus(EmploymentStatus.FULL_TIME)
    .workplaceTypes(Arrays.asList(WorkplaceTypes.ON_SITE))
    .p4PBudget(p4PBudget)
    .build();

// Build JobPostingRequest
JobPostingRequest jobPostingRequest = JobPostingRequest.builder()
    .elements(Arrays.asList(jobPosting))
    .build();

// Send JobPostingRequest using P4PJobPostingClient
JobPostingResponse jobPostingResponse = p4pJobPostingClient.post(jobPostingRequest);
```

> [!NOTE]
> The taskStatus and jobPostingStatus handling for P4P Job Postings remains consistent with that of basic job postings. However, all related operations will be performed using the P4PJobPostingClient.

##### Retrieve P4PJobPerformanceReports By Ids
```java
P4PJobReportsRequestByIds p4PJobReportsRequestByIds = P4PJobReportsRequestByIds
    .builder()
    .ids(Arrays.asList("external job posting id"))
    .dateRange(DateRange.builder()
        .start(Date.builder().day(10).month(05).year(2025).build())
        .end(Date.builder().day(12).month(05).year(2025).build())
        .build())
    .partnerContractId("enter partner contract id")
    .build();

P4PReportResponseByIds p4PReportResponseByIds = p4pJobPostingClient
    .getP4PReportByIds(p4PJobReportsRequestByIds);
```

##### Retrieve P4PJobPerformanceReports By Date
```java
P4PJobReportsRequestByDate p4PJobReportsRequestByDate = P4PJobReportsRequestByDate
    .builder()
    .dateRange(DateRange.builder()
        .start(Date.builder().day(10).month(05).year(2025).build())
        .end(Date.builder().day(12).month(05).year(2025).build())
        .build())
    .partnerContractId("enter partner contract id")
    .build();

P4PReportResponseByDate p4PReportResponseByDate = p4pJobPostingClient
    .getP4PReportsByDate(p4PJobReportsRequestByDate);
```

##### Retrieve Budget Reports
```java
P4PBudgetReportResponse p4PBudgetReportResponse = p4pJobPostingClient
    .getPartnerBudgetReports("enter partner contract id");
```

#### **ApplyConnect JobPosting**

##### Create JobPosting with ApplyConnect
```java
// Create ApplyConnectJobPostingClient instance using customer credentials
ApplyConnectJobPostingClient applyConnectJobPostingClient = LinkedInClientFactory.getInstance()
    .getApplyConnectJobPostingClient("customer-clientid", "customer-clientsecret");

// Build OnsiteApplyConfiguration for ApplyConnect JobPosting. 
// You have to customize SimpleTalentQuestions as per your need.
OnsiteApplyConfiguration onsiteApplyConfiguration = OnsiteApplyConfiguration
    .builder()
    .jobApplicationWebhookUrl("https://yourcompany.com/webhook")
    .questions(SimpleTalentQuestions.builder()
        .additionalQuestions(additionalQuestions)
        .build())
    .build();

JobPosting jobPosting = JobPosting.builder()
    .externalJobPostingId("external job posting id")
    .companyId("companyId")
    .companyApplyUrl("https://careers.yourcompany.com")
    .jobPostingOperationType(JobPostingOperationType.CREATE)
    .title("Software Developer in Test")
    .description("Testing Job")
    .location("Sunnyvale, California")
    .listedAt(System.currentTimeMillis())
    .listingType(ListingType.BASIC)
    .employmentStatus(EmploymentStatus.FULL_TIME)
    .workplaceTypes(Arrays.asList(WorkplaceTypes.ON_SITE))
    .onsiteApplyConfiguration(onsiteApplyConfiguration)
    .build();

// Build JobPostingRequest
JobPostingRequest jobPostingRequest = JobPostingRequest.builder()
    .elements(Arrays.asList(jobPosting))
    .build();

// Send JobPostingRequest using ApplyConnectJobPostingClient
JobPostingResponse jobPostingResponse = applyConnectJobPostingClient
    .post(jobPostingRequest);
```

> [!NOTE]
> The taskStatus and jobPostingStatus handling for ApplyConnect Job Postings remains consistent with that of basic job postings. However, all related operations should be performed using the ApplyConnectJobPostingClient.
