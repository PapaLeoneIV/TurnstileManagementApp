export  class RoleDTO {
    description: string;
    level: number;

    constructor(description: string, level: number) {
        this.description = description;
        this.level = level;
    }
}
