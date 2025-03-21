export  class ApiResponse<T> {
    status : string ;
    message: string;
    data : T[];
    timestamp: string;
    metadata : Object;
    constructor(data: T[], status: string, message: string, timestamp: string, metadata: Object) {
        this.data = data;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.metadata = metadata;
    }
}
